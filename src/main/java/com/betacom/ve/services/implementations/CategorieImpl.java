package com.betacom.ve.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.ve.dto.input.KeyStringReq;
import com.betacom.ve.dto.output.KeyStringDTO;
import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.mappers.KeyStringMapper;
import com.betacom.ve.models.Categorie;
import com.betacom.ve.repositories.ICategorieRepository;
import com.betacom.ve.services.interfaces.ICategorieServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategorieImpl implements ICategorieServices{
	private final ICategorieRepository catR;
	
	@Transactional
	@Override
	public void create(KeyStringReq req) throws Exception {
		log.debug("create {}", req);
		Categorie cat = new Categorie();
		cat.setId(req.getKey());
		cat.setNome(req.getValue());
		
		catR.save(cat);
		
	}
	
	@Transactional
	@Override
	public void delete(String id) throws Exception {
		log.debug("delete {}", id);
		Categorie cat = catR.findById(id)
				.orElseThrow(() -> new AcademyException("key_ntfnd"));
		if (!cat.getVeicolos().isEmpty())
			new AcademyException("key_inuse");
		catR.delete(cat);
	}

	@Override
	public List<KeyStringDTO> list(String categoria) {
		log.debug("List: {}" , categoria);
		List<Categorie> lC = catR.findByFilter(categoria);
		
		return KeyStringMapper.keyStringToDTO(lC);
	}

}
