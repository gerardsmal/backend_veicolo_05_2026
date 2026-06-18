package com.betacom.ve.services.implementations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.ve.dto.input.VeicoloReq;
import com.betacom.ve.dto.output.VeicoloDTO;
import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.mappers.VeicoloMapper;
import com.betacom.ve.models.Categorie;
import com.betacom.ve.models.Colore;
import com.betacom.ve.models.Marca;
import com.betacom.ve.models.TipoAlimentazione;
import com.betacom.ve.models.TipoVeicolo;
import com.betacom.ve.models.Veicolo;
import com.betacom.ve.repositories.ICategorieRepository;
import com.betacom.ve.repositories.IColoreRepository;
import com.betacom.ve.repositories.IMarcaRepository;
import com.betacom.ve.repositories.ITipoAlimentazioneRepository;
import com.betacom.ve.repositories.ITipoVeicoloRepository;
import com.betacom.ve.repositories.IVeicoloRepository;
import com.betacom.ve.services.interfaces.IVeicoliServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class VeicoloImpl implements IVeicoliServices{
	private final IVeicoloRepository veR;
	private final ITipoVeicoloRepository tipoVR;
	private final ICategorieRepository  catR;
	private final ITipoAlimentazioneRepository tipoAR;
	private final IColoreRepository colR;
	private final IMarcaRepository  marR;
	private final VeicoloMapper  veiM;
	
	@Transactional 
	@Override
	public Integer create(VeicoloReq req) throws Exception {
		log.debug("create {}" , req);
		Veicolo v = new Veicolo();
		TipoVeicolo tV = tipoVR.findById(req.getTipoVeicolo())
			.orElseThrow(() -> new AcademyException("tipo_veicolo_invalid" + ":" + req.getTipoVeicolo()));
		v.setTipoVeicolo(tV);
		Categorie cat = catR.validazioneById(req.getCategorie(), tV.getPattern())
				.orElseThrow(() -> new AcademyException("cat_invalid"));
		v.setCategorie(cat);
		TipoAlimentazione tipoA = tipoAR.validazioneById(req.getTipoAlimentazione(), tV.getPattern())
				.orElseThrow(() -> new AcademyException("alim_invalid"));
		v.setTipoAlimentazione(tipoA);
		Colore col = colR.findById(req.getColore())
				.orElseThrow(() -> new AcademyException("colore_invalid"));
		v.setColore(col);
		Marca marca = marR.findById(req.getMarca())
				.orElseThrow(() -> new AcademyException("marca_invalid"));
		v.setMarca(marca);
		
		if (req.getAnnoProduzione() < LocalDate.now().getYear()- 10 || req.getAnnoProduzione() > LocalDate.now().getYear())
			throw new AcademyException("anno_invalid");
		v.setAnnoProduzione(req.getAnnoProduzione());

		v.setModello(req.getModello());	
		v.setNumeroRuote(req.getNumeroRuote());
		v.setAnnoProduzione(req.getAnnoProduzione());
		v.setPrezzo(req.getPrezzo());
		v.setDataInserimento(LocalDate.now());	
		
		return veR.save(v).getId();
	}

	@Transactional
	@Override
	public void update(VeicoloReq req, Veicolo v) throws Exception {
		log.debug("Update via macchina {}", req);
		
		Optional.ofNullable(req.getCategorie())
		    .map(id -> catR.findById(id)
		        .orElseThrow(() -> new AcademyException("cat_invalid" + ":" + id)))
		    .ifPresent(v::setCategorie);
		
		Optional.ofNullable(req.getTipoAlimentazione())
		    .map(id -> tipoAR.findById(id)
		        .orElseThrow(() -> new AcademyException("alim_invalid" + ":" + id)))
		    .ifPresent(v::setTipoAlimentazione);
		
		Optional.ofNullable(req.getMarca())
		    .map(id -> marR.findById(id)
		        .orElseThrow(() -> new AcademyException("marca_invalid" + ":" + id)))
		    .ifPresent(v::setMarca);

		Optional.ofNullable(req.getColore())
		    .map(id -> colR.findById(id)
		        .orElseThrow(() -> new AcademyException("colore_invalid" + ":" + id)))
		    .ifPresent(v::setColore);

		Optional.ofNullable(req.getAnnoProduzione())
		    .ifPresent(anno -> {
		        int annoCorrente = LocalDate.now().getYear();
		        if (anno < annoCorrente - 10 || anno > annoCorrente) {
		            throw new AcademyException("anno_invalid" + ":" + anno
		            );
		        }
		        v.setAnnoProduzione(anno);	    
		    });
		Optional.ofNullable(req.getModello()).ifPresent(v::setModello);
		Optional.ofNullable(req.getNumeroRuote()).ifPresent(v::setNumeroRuote);
		Optional.ofNullable(req.getPrezzo()).ifPresent(v::setPrezzo);
		veR.save(v);
	}

	@Override
	public List<VeicoloDTO> find(Integer id, Integer tipo, String categoria, String alimentazione, Integer colore,
			Integer marca, String targa, Integer porte) {
		log.debug("find {}/{}/{}/{}/{}/{}/{}/{}", id, tipo, categoria, alimentazione, colore, marca, targa, porte);
		
		List<Veicolo> lV = veR.searchByFilter(id, tipo, categoria, alimentazione, colore, marca, targa, porte);
		
		log.debug("Size:" + lV.size());
		
		
		return veiM.builVeicoloDTO(lV);
	}

	@Override
	public VeicoloDTO getById(Integer id) throws Exception {
		log.debug("getById {}", id);
		Veicolo v = veR.findById(id)
				.orElseThrow(() -> new AcademyException("veicolo_ntfnd"));
		
		return veiM.builVeicoloDTO(v);
	}
}
