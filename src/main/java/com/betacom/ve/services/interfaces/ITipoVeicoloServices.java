package com.betacom.ve.services.interfaces;

import java.util.List;

import com.betacom.ve.dto.input.KeyStringReq;
import com.betacom.ve.dto.input.TipoVeicoloReq;
import com.betacom.ve.dto.output.KeyStringDTO;
import com.betacom.ve.dto.output.TipoVeicoloDTO;

public interface ITipoVeicoloServices {

	void create(TipoVeicoloReq req) throws Exception;
	void delete(Integer id) throws Exception;
	
	List<TipoVeicoloDTO> list();
}
