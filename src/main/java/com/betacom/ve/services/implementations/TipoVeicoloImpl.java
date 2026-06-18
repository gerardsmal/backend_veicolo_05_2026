package com.betacom.ve.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.ve.dto.input.KeyIntegerReq;
import com.betacom.ve.dto.input.KeyStringReq;
import com.betacom.ve.dto.input.TipoVeicoloReq;
import com.betacom.ve.dto.output.KeyIntegerDTO;
import com.betacom.ve.dto.output.KeyStringDTO;
import com.betacom.ve.dto.output.TipoVeicoloDTO;
import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.mappers.KeyIntegerMapper;
import com.betacom.ve.mappers.KeyStringMapper;
import com.betacom.ve.mappers.TipoVeicoloMapper;
import com.betacom.ve.models.Categorie;
import com.betacom.ve.models.Colore;
import com.betacom.ve.models.TipoVeicolo;
import com.betacom.ve.repositories.ICategorieRepository;
import com.betacom.ve.repositories.IColoreRepository;
import com.betacom.ve.repositories.ITipoVeicoloRepository;
import com.betacom.ve.services.interfaces.ICategorieServices;
import com.betacom.ve.services.interfaces.IColoreServices;
import com.betacom.ve.services.interfaces.ITipoVeicoloServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TipoVeicoloImpl implements ITipoVeicoloServices{
	private final ITipoVeicoloRepository tipoR;
	
	@Transactional
	@Override
	public void create(TipoVeicoloReq req) throws Exception {
		log.debug("create {}", req);
		if (tipoR.existsByNome(req.getNome()))
			throw new AcademyException("tipo_veicolo_exits");
		
		TipoVeicolo tV = new TipoVeicolo();
		tV.setNome(req.getNome());
		tV.setPattern(req.getPattern());
		tV.setStatus(req.getStatus() == null ? true : req.getStatus());
		tipoR.save(tV);
	}
	
	@Transactional
	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete {}", id);
		TipoVeicolo tV = tipoR.findById(id)
				.orElseThrow(() -> new AcademyException("key_ntfnd"));
		tipoR.delete(tV);
	}

	@Override
	public List<TipoVeicoloDTO> list() {
		log.debug("List");
		List<TipoVeicolo> lC = tipoR.findAll();
		
		return TipoVeicoloMapper.tipoVeicoloToDTO(lC);
	}

}
