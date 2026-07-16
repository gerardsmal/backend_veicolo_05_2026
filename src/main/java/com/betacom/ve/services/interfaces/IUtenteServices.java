package com.betacom.ve.services.interfaces;

import java.util.List;

import com.betacom.ve.dto.input.LoginReq;
import com.betacom.ve.dto.input.UtenteReq;
import com.betacom.ve.dto.output.LoginDTO;
import com.betacom.ve.dto.output.UtenteDTO;

public interface IUtenteServices {
	void create(UtenteReq req) throws Exception;
	void update(UtenteReq req) throws Exception;
	void delete(String userName) throws Exception;
	
	List<UtenteDTO> list(String userName, String nome, String cognome, String role);
	UtenteDTO  getById(String userName);
	
	LoginDTO login(LoginReq req) throws Exception;
	
}
