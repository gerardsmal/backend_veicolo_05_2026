package com.betacom.ve.services.interfaces;


import java.util.List;

import com.betacom.ve.dto.input.VeicoloReq;
import com.betacom.ve.dto.output.VeicoloDTO;
import com.betacom.ve.dto.output.page.PageResponseDTO;
import com.betacom.ve.models.Veicolo;

public interface IVeicoliServices {
Integer create(VeicoloReq req) throws Exception;
	
	void update(VeicoloReq req, Veicolo veicolo) throws Exception;
		
	List<VeicoloDTO> find(Integer id, Integer tipo, String categoria, 
			String alimentazione, Integer colore, Integer marca, String targa, Integer porte);
	PageResponseDTO<VeicoloDTO> findByPage(Integer page, Integer size, String sortBy, String direction,Integer id, Integer tipo, String categoria, 
			String alimentazione, Integer colore, Integer marca, String targa, Integer porte);
	
	VeicoloDTO getById(Integer id) throws Exception;
	
	
}
