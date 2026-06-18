package com.betacom.ve.services.interfaces;

import java.util.List;

import com.betacom.ve.dto.input.KeyIntegerReq;
import com.betacom.ve.dto.output.KeyIntegerDTO;

public interface IMarcaServices {

	void create(KeyIntegerReq req) throws Exception;
	void delete(Integer id) throws Exception;
	
	List<KeyIntegerDTO> list();
}
