package com.betacom.ve.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ve.dto.input.CarelloReq;
import com.betacom.ve.dto.input.ValidationGroups;
import com.betacom.ve.dto.output.ResponseDTO;
import com.betacom.ve.services.interfaces.ICarelloServices;
import com.betacom.ve.services.interfaces.IMessageServices;
import com.betacom.ve.services.interfaces.IUtenteServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/carello")
public class CarelloController {
	private final ICarelloServices carS;
	private final IMessageServices msgS;

	@PostMapping("/user/addRiga")
	public ResponseEntity<ResponseDTO> addRiga(Authentication authentication,
			@RequestBody(required = true) @Validated(ValidationGroups.Create.class) CarelloReq req) throws Exception {

		ResponseDTO r = new ResponseDTO();
		req.setUtenteID(authentication.getName());
		carS.addRiga(req);
		r.setMsg(msgS.get("rest_created"));
		return ResponseEntity.ok(r);
	}

	@PatchMapping("/user/updateRiga")
	public ResponseEntity<ResponseDTO> updateRiga(Authentication authentication,
			@RequestBody(required = true) @Validated(ValidationGroups.Update.class) CarelloReq req) throws Exception {
		ResponseDTO r = new ResponseDTO();
		req.setUtenteID(authentication.getName());
		carS.updateRiga(req);
		r.setMsg(msgS.get("rest_updated"));
		return ResponseEntity.ok(r);
	}

	@DeleteMapping("/user/deleteRiga/{id}")
	public ResponseEntity<ResponseDTO> delete(Authentication authentication,
			@PathVariable(required = true) Integer id) throws Exception {
		ResponseDTO r = new ResponseDTO();
		carS.deleteRiga(authentication.getName(),id);
		r.setMsg(msgS.get("rest_deleted"));
		return ResponseEntity.ok(r);
	}

	@GetMapping("/user/list")
	public ResponseEntity<Object> list(Authentication authentication) throws Exception{

		return ResponseEntity.ok(carS.getCarello(authentication.getName()));

	}
}
