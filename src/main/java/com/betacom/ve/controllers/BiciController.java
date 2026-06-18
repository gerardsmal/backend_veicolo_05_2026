package com.betacom.ve.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ve.dto.input.BiciReq;
import com.betacom.ve.dto.input.ValidationGroups;
import com.betacom.ve.dto.output.ResponseDTO;
import com.betacom.ve.services.interfaces.IBiciServices;
import com.betacom.ve.services.interfaces.IMessageServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping ("rest/bici")
public class BiciController {

	private final IBiciServices biciS;
	private final IMessageServices   msgS;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) BiciReq req) 
			throws Exception{
		ResponseDTO r = new ResponseDTO();
		biciS.create(req);
		r.setMsg(msgS.get("rest_created"));
		return ResponseEntity.ok(r);		
	}
	
	@PatchMapping("/update")
	public ResponseEntity<ResponseDTO> update(@RequestBody(required = true) @Validated(ValidationGroups.Update.class) BiciReq req) 
			throws Exception{
		ResponseDTO r = new ResponseDTO();
		biciS.update(req);
		r.setMsg(msgS.get("rest_updated"));
		return ResponseEntity.ok(r);		
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable(required = true)  Integer id) throws Exception{
		ResponseDTO r = new ResponseDTO();
		biciS.delete(id);
		r.setMsg(msgS.get("rest_deleted"));
		return ResponseEntity.ok(r);		
	}

}
