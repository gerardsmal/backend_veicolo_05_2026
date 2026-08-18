package com.betacom.ve.services.interfaces;

import com.betacom.ve.dto.input.CarelloReq;
import com.betacom.ve.dto.output.CarelloDTO;

public interface ICarelloServices {
	void addRiga(CarelloReq req) throws Exception;
	void updateRiga(CarelloReq req) throws Exception;
	void deleteRiga(Long userId,Integer id) throws Exception;
	
	CarelloDTO getCarello(Long userId) throws Exception;
}
