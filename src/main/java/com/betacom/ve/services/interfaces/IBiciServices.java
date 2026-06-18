package com.betacom.ve.services.interfaces;

import com.betacom.ve.dto.input.BiciReq;

public interface IBiciServices {
	void create(BiciReq req) throws Exception;
	void update(BiciReq req) throws Exception;
	void delete(Integer id) throws Exception;
}
