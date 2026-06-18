package com.betacom.ve.services.interfaces;

import java.util.List;

import com.betacom.ve.dto.input.KeyStringReq;
import com.betacom.ve.dto.output.KeyStringDTO;

public interface ICategorieServices {

	void create(KeyStringReq req) throws Exception;
	void delete(String id) throws Exception;
	
	List<KeyStringDTO> list(String categoria);
}
