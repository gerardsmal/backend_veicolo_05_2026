package com.betacom.ve.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.ve.dto.input.KeyStringReq;
import com.betacom.ve.dto.output.KeyStringDTO;
import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.mappers.KeyStringMapper;
import com.betacom.ve.models.TipoAlimentazione;
import com.betacom.ve.repositories.ITipoAlimentazioneRepository;
import com.betacom.ve.services.interfaces.ITipoAlimentazioneServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TipoAlimentazioneImpl implements ITipoAlimentazioneServices{
	private final ITipoAlimentazioneRepository tipoR;
	
	@Transactional
	@Override
	public void create(KeyStringReq req) throws Exception {
		log.debug("create {}", req);
		TipoAlimentazione alim = new TipoAlimentazione();
		alim.setId(req.getKey());
		alim.setNome(req.getValue());
		
		tipoR.save(alim);
		
	}
	
	@Transactional
	@Override
	public void delete(String id) throws Exception {
		log.debug("delete {}", id);
		TipoAlimentazione alim = tipoR.findById(id)
				.orElseThrow(() -> new AcademyException("key_ntfnd"));
		if (!alim.getVeicolos().isEmpty())
			new AcademyException("key_inuse");
		tipoR.delete(alim);
	}

	@Override
	public List<KeyStringDTO> list(String categoria) {
		log.debug("List: {}" , categoria);
		List<TipoAlimentazione> lC = tipoR.findByFilter(categoria);
		
		return KeyStringMapper.keyStringToDTO(lC);
	}

}
