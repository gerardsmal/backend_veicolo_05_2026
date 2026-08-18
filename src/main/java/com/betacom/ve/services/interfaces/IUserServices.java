package com.betacom.ve.services.interfaces;

import java.util.List;

import com.betacom.ve.dto.input.ChangePwdReq;
import com.betacom.ve.dto.input.LoginReq;
import com.betacom.ve.dto.input.UserReq;
import com.betacom.ve.dto.output.MeDTO;
import com.betacom.ve.dto.output.UserDTO;

public interface IUserServices {
	void create(UserReq req) throws Exception;
	void update(UserReq req) throws Exception;
	void delete(Long id) throws Exception;
	
	void changePwd(ChangePwdReq req) throws Exception;
	void sendResetPassword(String userName) throws Exception;
	void resetPassword(ChangePwdReq req) throws Exception;
	
	List<UserDTO> list(String userName, String nome, String cognome);
	UserDTO  getById(Long id);
	
	MeDTO me(LoginReq req) throws Exception;
	
}
