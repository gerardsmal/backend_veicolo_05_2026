package com.betacom.ve.services.implementations;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.ve.dto.input.MacchinaReq;
import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.models.Macchina;
import com.betacom.ve.models.Veicolo;
import com.betacom.ve.repositories.IMacchinaRepository;
import com.betacom.ve.repositories.IVeicoloRepository;
import com.betacom.ve.services.interfaces.IMacchinaServices;
import com.betacom.ve.services.interfaces.IVeicoliServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MacchinaImpl implements IMacchinaServices{
	private final IMacchinaRepository macR;
	private final IVeicoliServices  veS;
	private final IVeicoloRepository  veR;
	
	@Transactional
	@Override
	public void create(MacchinaReq req) throws Exception {
		log.debug("create {}", req);
		Veicolo veicolo = veR.findById(veS.create(req))
				.orElseThrow(() -> new AcademyException("fatal"));
		
		Macchina mac = new Macchina();
		mac.setVeicolo(veicolo);
		
		if (macR.existsByTarga(req.getTarga()))
			throw new AcademyException("targa_exist" );
		mac.setTarga(req.getTarga().toUpperCase());
		mac.setNumeroPorte(req.getNumeroPorte());
		mac.setCc(req.getCc());
		
		macR.save(mac);
	}
	
	@Transactional
	@Override
	public void update(MacchinaReq req) throws Exception {

		log.debug("update {}", req);
		
		Veicolo v = veR.findById(req.getId())
				.orElseThrow(() -> new AcademyException("mac_ntfnd"));
		
		
		Macchina mac = v.getMacchina();
		
		Optional.ofNullable(req.getTarga())
	    .ifPresent(t -> {
			if (macR.existsByTarga(req.getTarga()))
				throw new AcademyException("targa_exist");
			mac.setTarga(req.getTarga());			
	    });

		Optional.ofNullable(req.getNumeroPorte()).ifPresent(mac::setNumeroPorte);
		Optional.ofNullable(req.getCc()).ifPresent(mac::setCc);
	
		macR.save(mac);	
	
		veS.update(req, v);
		
		
	}

	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete {}", id);
		Macchina mac = macR.findById(id)
				.orElseThrow(() -> new AcademyException("mac_ntfnd"));
		
		macR.delete(mac);
		
	}

}
