package com.betacom.ve.services.implementations;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.ve.dto.input.MotoReq;
import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.models.Macchina;
import com.betacom.ve.models.Moto;
import com.betacom.ve.models.Veicolo;
import com.betacom.ve.repositories.IMotoRepository;
import com.betacom.ve.repositories.IVeicoloRepository;
import com.betacom.ve.services.interfaces.IMotoServices;
import com.betacom.ve.services.interfaces.IVeicoliServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MotoImpl implements IMotoServices{
	private final IMotoRepository motR;
	private final IVeicoliServices  veS;
	private final IVeicoloRepository  veR;
	
	@Transactional
	@Override
	public void create(MotoReq req) throws Exception {
		log.debug("create {}", req);
		Veicolo veicolo = veR.findById(veS.create(req))
				.orElseThrow(() -> new AcademyException("fatal"));
		
		Moto moto = new Moto();
		moto.setVeicolo(veicolo);
		
		if (motR.existsByTarga(req.getTarga()))
			throw new AcademyException("targa_exist" );
		moto.setTarga(req.getTarga().toUpperCase());
		moto.setCc(req.getCc());
		
		motR.save(moto);
		
	}

	@Transactional
	@Override
	public void update(MotoReq req) throws Exception {
		log.debug("update {}", req);
		
		Veicolo v = veR.findById(req.getId())
				.orElseThrow(() -> new AcademyException("veicolo_ntfnd"));
		
		
		Moto moto = v.getMoto();
		
		Optional.ofNullable(req.getTarga())
	    .ifPresent(t -> {
			if (motR.existsByTarga(req.getTarga()))
				throw new AcademyException("targa_exist");
			moto.setTarga(req.getTarga());			
	    });

		Optional.ofNullable(req.getCc()).ifPresent(moto::setCc);
	
		motR.save(moto);	
	
		veS.update(req, v);

		
	}

	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete {}", id);
		Moto moto = motR.findById(id)
				.orElseThrow(() -> new AcademyException("veicolo_ntfnd"));
		
		motR.delete(moto);
		
	}

}
