package com.betacom.ve.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.ve.dto.input.KeyIntegerReq;
import com.betacom.ve.dto.input.KeyStringReq;
import com.betacom.ve.dto.output.KeyIntegerDTO;
import com.betacom.ve.dto.output.KeyStringDTO;
import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.mappers.KeyIntegerMapper;
import com.betacom.ve.mappers.KeyStringMapper;
import com.betacom.ve.models.Categorie;
import com.betacom.ve.models.Colore;
import com.betacom.ve.repositories.ICategorieRepository;
import com.betacom.ve.repositories.IColoreRepository;
import com.betacom.ve.services.interfaces.ICategorieServices;
import com.betacom.ve.services.interfaces.IColoreServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ColoreImpl implements IColoreServices{
	private final IColoreRepository colR;
	
	@Transactional
	@Override
	public void create(KeyIntegerReq req) throws Exception {
		log.debug("create {}", req);
		Colore col = new Colore();
		col.setNome(req.getValue());
		
		colR.save(col);
		
	}
	
	@Transactional
	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete {}", id);
		Colore cat = colR.findById(id)
				.orElseThrow(() -> new AcademyException("key_ntfnd"));
		if (!cat.getVeicolos().isEmpty())
			new AcademyException("key_inuse");
		colR.delete(cat);
	}

	@Override
	public List<KeyIntegerDTO> list() {
		log.debug("List");
		List<Colore> lC = colR.findAll();
		
		return KeyIntegerMapper.keyIntegerToDTO(lC);
	}

}
