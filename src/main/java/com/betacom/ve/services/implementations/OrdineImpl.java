package com.betacom.ve.services.implementations;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.betacom.ve.enums.StatoCarello;
import com.betacom.ve.enums.StatoPagamento;
import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.models.Carello;
import com.betacom.ve.models.CarelloDetails;
import com.betacom.ve.models.Ordini;
import com.betacom.ve.models.OrdiniDetails;
import com.betacom.ve.models.Spedizione;
import com.betacom.ve.models.Utente;
import com.betacom.ve.repositories.ICarelloRepository;
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
}
