package com.betacom.ve.services.interfaces;

import com.betacom.ve.dto.input.MotoReq;

public interface IMotoServices {
	void create(MotoReq req) throws Exception;
	void update(MotoReq req) throws Exception;
	void delete(Integer id) throws Exception;
}
