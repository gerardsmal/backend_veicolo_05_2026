package com.betacom.ve.services.implementations;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.betacom.ve.dto.input.OrdiniReq;
import com.betacom.ve.dto.input.SpedizioneReq;
import com.betacom.ve.dto.output.ModalitaPagamentoDTO;
import com.betacom.ve.dto.output.OrdiniDTO;
import com.betacom.ve.dto.output.SpedizioneDTO;
import com.betacom.ve.enums.StatoCarello;
import com.betacom.ve.enums.StatoPagamento;
import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.mappers.ModalitaPagamentoBuilder;
import com.betacom.ve.mappers.OrdiniMapper;
import com.betacom.ve.mappers.SpedizioneMapper;
import com.betacom.ve.models.Carello;
import com.betacom.ve.models.CarelloDetails;
import com.betacom.ve.models.Contatore;
import com.betacom.ve.models.ModalitaPagamento;
import com.betacom.ve.models.Ordini;
import com.betacom.ve.models.OrdiniDetails;
import com.betacom.ve.models.Spedizione;
import com.betacom.ve.models.Utente;
import com.betacom.ve.repositories.ICarelloRepository;
import com.betacom.ve.repositories.IContatoreRepository;
import com.betacom.ve.repositories.IModalitaPagamentoRepository;
import com.betacom.ve.repositories.IOrdiniDetailsRepository;
import com.betacom.ve.repositories.IOrdiniRepository;
import com.betacom.ve.repositories.ISpedizioneRepository;
import com.betacom.ve.repositories.IUtenteRepository;
import com.betacom.ve.services.interfaces.IOrdineServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrdineImpl implements IOrdineServices {

	private final IUtenteRepository utenteR;
	private final IOrdiniRepository ordineR;
	private final IOrdiniDetailsRepository ordineDetailsR;
	private final ICarelloRepository carelloR;
	private final ISpedizioneRepository spedizioneR;
	private final IModalitaPagamentoRepository modalitaPagamentoR;
	private final IContatoreRepository contatoreR;

	private Ordini findOrdineInCorso(Utente utente, Integer ordineId, String codiceErrore) {
		if (utente.getOrdini() == null)
			throw new AcademyException(codiceErrore);

		return utente.getOrdini().stream()
				.filter(o -> o.getStatusPagamento() == StatoPagamento.IN_CORSO)
				.filter(o -> ordineId == null || Objects.equals(o.getId(), ordineId))
				.findFirst()
				.orElseThrow(() -> new AcademyException(codiceErrore));
	}

	@Transactional
	public long prossimoNumeroOrdine(int anno) {
		Contatore contatore = contatoreR.findByTipoForUpdate("ORDINI")
				.orElseThrow(() -> new IllegalStateException("Contatore ORDINI non inizializzato"));

		if (!contatore.getAnno().equals(anno)) {
			contatore.setAnno(anno);
			contatore.setUltimoNumero(1L);
		} else {
			contatore.setUltimoNumero(contatore.getUltimoNumero() + 1);
		}

		contatoreR.save(contatore);
		return contatore.getUltimoNumero();
	}

	@Transactional
	@Override
	public void create(String userName) throws Exception {
		log.debug("create {}", userName);

		Utente utente = utenteR.findById(userName)
				.orElseThrow(() -> new AcademyException("account_ntfnd"));

		if (utente.getSpedizione() == null || utente.getSpedizione().isEmpty()) {
			Spedizione spedizione = new Spedizione();
			spedizione.setPredefinito(true);
			spedizione.setCognome(utente.getCognome());
			spedizione.setNome(utente.getNome());
			spedizione.setVia(utente.getVia());
			spedizione.setCommune(utente.getComune());
			spedizione.setCap(utente.getCap());
			spedizione.setUtente(utente);
			spedizioneR.save(spedizione);
		}

		Carello carello = utente.getCarello();
		if (carello == null)
			throw new AcademyException("order_carello_empty");

		if (carello.getStato() == StatoCarello.ordine)
			throw new AcademyException("carello_not_available");

		if (carello.getRigaCarello() == null || carello.getRigaCarello().isEmpty())
			throw new AcademyException("order_carello_empty");

		Ordini ordine = new Ordini();
		ordine.setStatusPagamento(StatoPagamento.IN_CORSO);
		ordine.setDataOrdine(LocalDate.now());
		ordine.setUtente(utente);
		ordineR.save(ordine);

		Double totaleOrdine = createOrdiniDetails(ordine, carello);
		ordine.setTotale(totaleOrdine);

		carello.setStato(StatoCarello.ordine);
		carelloR.save(carello);
		ordineR.save(ordine);
	}

	@Transactional
	@Override
	public Double createOrdiniDetails(Ordini ordine, Carello carello) throws Exception {
		log.debug("createOrdiniDetails ordine:{} carello:{}", ordine.getId(), carello.getId());

		double totaleOrdine = 0.0;
		for (CarelloDetails carelloDetails : carello.getRigaCarello()) {
			OrdiniDetails ordineDetails = new OrdiniDetails();
			ordineDetails.setDataCreazione(carelloDetails.getDataCreazione());
			ordineDetails.setQuantita(carelloDetails.getQuantita());
			ordineDetails.setProductName(carelloDetails.getVeicolo().getModello());
			ordineDetails.setImage(carelloDetails.getVeicolo().getImage());
			ordineDetails.setPrezzoUnit(carelloDetails.getVeicolo().getPrezzo());

			double prezzoRiga = carelloDetails.getVeicolo().getPrezzo() * carelloDetails.getQuantita();
			ordineDetails.setPrezzo(prezzoRiga);
			ordineDetails.setOrdini(ordine);
			ordineDetailsR.save(ordineDetails);

			totaleOrdine += prezzoRiga;
		}

		return totaleOrdine;
	}

	@Override
	public List<SpedizioneDTO> listSpedizione(String userName) throws Exception {
		log.debug("listSpedizione {}", userName);

		Utente utente = utenteR.findById(userName)
				.orElseThrow(() -> new AcademyException("account_ntfnd"));

		return SpedizioneMapper.spedizioneToDTO(utente.getSpedizione());
	}

	@Transactional
	@Override
	public void createSpedizione(String userName, SpedizioneReq req) throws Exception {
		log.debug("createSpedizione {}", userName);

		Utente utente = utenteR.findById(userName)
				.orElseThrow(() -> new AcademyException("account_ntfnd"));

		Spedizione spedizione = new Spedizione();
		spedizione.setPredefinito(false);
		spedizione.setNome(req.getNome());
		spedizione.setCognome(req.getCognome());
		spedizione.setVia(req.getVia());
		spedizione.setCommune(req.getCommune());
		spedizione.setCap(req.getCap());
		spedizione.setUtente(utente);
		spedizioneR.save(spedizione);
	}

	@Transactional
	@Override
	public void removeSpedizione(String userName, Integer id) throws Exception {
		log.debug("removeSpedizione {}/{}", userName, id);

		Utente utente = utenteR.findById(userName)
				.orElseThrow(() -> new AcademyException("account_ntfnd"));

		Spedizione spedizione = utente.getSpedizione() == null ? null : utente.getSpedizione().stream()
				.filter(s -> Objects.equals(s.getId(), id))
				.findFirst()
				.orElse(null);
		if (spedizione == null)
			throw new AcademyException("spedizione_ntfnd");

		spedizioneR.delete(spedizione);
	}

	@Transactional
	@Override
	public void assignSpedizione(String userName, OrdiniReq req) throws Exception {
		log.debug("assignSpedizione {}/{}", userName, req.getId());

		Utente utente = utenteR.findById(userName)
				.orElseThrow(() -> new AcademyException("account_ntfnd"));

		if (req.getId() == null)
			throw new AcademyException("ordine_invalid");
		Ordini ordine = findOrdineInCorso(utente, req.getId(), "ordine_invalid");

		Spedizione spedizione = utente.getSpedizione() == null ? null : utente.getSpedizione().stream()
				.filter(s -> Objects.equals(s.getId(), req.getSpedizioneID()))
				.findFirst()
				.orElse(null);
		if (spedizione == null)
			throw new AcademyException("spedizione_ntfnd");

		ordine.setSpedizione(spedizione);
		ordineR.save(ordine);
	}

	@Override
	public List<ModalitaPagamentoDTO> listModalitaPagamento() throws Exception {
		log.debug("listModalitaPagamento");
		return ModalitaPagamentoBuilder.modalitaPagamentoToDTO(modalitaPagamentoR.findAll());
	}

	@Transactional
	@Override
	public void assignModalitaPagamento(String userName, OrdiniReq req) throws Exception {
		log.debug("assignModalitaPagamento {}/{}", userName, req.getId());

		Utente utente = utenteR.findById(userName)
				.orElseThrow(() -> new AcademyException("account_ntfnd"));

		if (req.getId() == null)
			throw new AcademyException("ordine_invalid");
		Ordini ordine = findOrdineInCorso(utente, req.getId(), "ordine_invalid");

		if (req.getModalitaPagamentoID() == null)
			throw new AcademyException("metodo_ntfnd");
		ModalitaPagamento modalitaPagamento = modalitaPagamentoR.findById(req.getModalitaPagamentoID())
				.orElseThrow(() -> new AcademyException("metodo_ntfnd"));

		ordine.setModalitaPagamento(modalitaPagamento);
		ordineR.save(ordine);
	}

	@Transactional
	@Override
	public OrdiniDTO confermo(String userName) throws Exception {
		log.debug("confermo {}", userName);

		Utente utente = utenteR.findById(userName)
				.orElseThrow(() -> new AcademyException("account_ntfnd"));

		Ordini ordine = findOrdineInCorso(utente, null, "ordinie-ntfnd");

		Carello carello = utente.getCarello();
		if (carello == null)
			throw new AcademyException("order_carello_empty");

		LocalDate dataInvio = LocalDate.now();
		ordine.setStatusPagamento(StatoPagamento.CONFERMATO);
		ordine.setDataInvio(dataInvio);
		ordine.setNumeroOrdine("%d-%06d".formatted(dataInvio.getYear(), prossimoNumeroOrdine(dataInvio.getYear())));
		ordineR.save(ordine);

		carello.getRigaCarello().clear();
		carello.setStato(StatoCarello.carello);
		carelloR.save(carello);

		return OrdiniMapper.ordiniToDTO(ordine);
	}

	@Transactional
	@Override
	public void remove(String userName) throws Exception {
		log.debug("remove {}", userName);

		Utente utente = utenteR.findById(userName)
				.orElseThrow(() -> new AcademyException("account_ntfnd"));

		Ordini ordine = findOrdineInCorso(utente, null, "order_not_cancelabile");

		Carello carello = utente.getCarello();
		if (carello == null)
			throw new AcademyException("order_carello_empty");

		ordineR.delete(ordine);
		carello.setStato(StatoCarello.carello);
		carelloR.save(carello);
	}
}
