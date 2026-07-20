package com.betacom.ve.services.interfaces;

import java.util.List;

import com.betacom.ve.dto.input.ChangePwdReq;
import com.betacom.ve.dto.input.LoginReq;
import com.betacom.ve.dto.input.UtenteReq;
import com.betacom.ve.dto.output.MeDTO;
import com.betacom.ve.dto.output.UtenteDTO;

public interface IUtenteServices {
	void create(UtenteReq req) throws Exception;
	void update(UtenteReq req) throws Exception;
	void delete(String userName) throws Exception;
	
	void changePwd(ChangePwdReq req) throws Exception;
	
	List<UtenteDTO> list(String userName, String nome, String cognome, String role);
	UtenteDTO  getById(String userName);
	
	MeDTO me(LoginReq req) throws Exception;
	
}
