package com.betacom.ve.services.implementations;

import java.beans.Transient;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.ve.dto.input.BiciReq;
import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.models.Bici;
import com.betacom.ve.models.Moto;
import com.betacom.ve.models.Sospenzione;
import com.betacom.ve.models.Veicolo;
import com.betacom.ve.repositories.IBiciRepository;
import com.betacom.ve.repositories.ISospenzioneRepository;
import com.betacom.ve.repositories.IVeicoloRepository;
import com.betacom.ve.services.interfaces.IBiciServices;
import com.betacom.ve.services.interfaces.IVeicoliServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BiciImpl implements IBiciServices{

	private final IBiciRepository biciR;
	private final IVeicoliServices  veS;
	private final IVeicoloRepository  veR;
	private final ISospenzioneRepository sosR;
	
	@Transactional
	@Override
	public void create(BiciReq req) throws Exception {
		log.debug("create {}", req);
		Veicolo veicolo = veR.findById(veS.create(req))
				.orElseThrow(() -> new AcademyException("fatal"));
		
		Bici bici = new Bici();
		bici.setVeicolo(veicolo);
		
		Sospenzione spsp = sosR.findById(req.getSospenzione())
				.orElseThrow(() -> new AcademyException("sos_invalid"));
		bici.setSospenzione(spsp);
		
		bici.setPieghevole(req.getPieghevole() == null ? false : req.getPieghevole());
		bici.setNumeroMarce(req.getNumeroMarce());
		biciR.save(bici);

	}
	@Transactional
	@Override
	public void update(BiciReq req) throws Exception {
		log.debug("update {}", req);
		
		Veicolo v = veR.findById(req.getId())
				.orElseThrow(() -> new AcademyException("veicolo_ntfnd"));
				
		Bici bici = v.getBici();
		
		Optional.ofNullable(req.getSospenzione())
	    .ifPresent(t -> {
	    	Sospenzione spsp = sosR.findById(req.getSospenzione())
					.orElseThrow(() -> new AcademyException("sos_invalid"));
			bici.setSospenzione(spsp);		
	    });
		
		Optional.ofNullable(req.getPieghevole()).ifPresent(bici::setPieghevole);
		Optional.ofNullable(req.getNumeroMarce()).ifPresent(bici::setNumeroMarce);

		biciR.save(bici);	
		
		veS.update(req,v);
		
	}
	@Transactional
	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete {}", id);
		Bici bi = biciR.findById(id)
				.orElseThrow(() -> new AcademyException("veicolo_ntfnd"));
		biciR.delete(bi);
		
	}

}
