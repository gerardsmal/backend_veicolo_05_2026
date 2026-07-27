package com.betacom.ve.services.interfaces;

import com.betacom.ve.dto.input.CarelloReq;
import com.betacom.ve.dto.output.CarelloDTO;

public interface ICarelloServices {
	void addRiga(CarelloReq req) throws Exception;
	void updateRiga(CarelloReq req) throws Exception;
	void deleteRiga(String userName,Integer id) throws Exception;
	
	CarelloDTO getCarello(String userName) throws Exception;
}
