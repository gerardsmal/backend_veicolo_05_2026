package com.betacom.ve.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ve.dto.input.UtenteReq;
import com.betacom.ve.dto.input.ValidationGroups;
import com.betacom.ve.dto.output.ResponseDTO;
import com.betacom.ve.services.interfaces.IMessageServices;
import com.betacom.ve.services.interfaces.IUtenteServices;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/utente")
public class UtenteController {
	
	private final IUtenteServices utS;
	private final IMessageServices   msgS;

	@PostMapping("/public/create")
	public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) UtenteReq req)
					throws Exception{
		ResponseDTO r = new ResponseDTO();

		utS.create(req);
		r.setMsg(msgS.get("rest_created"));
		return ResponseEntity.ok(r);			
	}
	
	@PatchMapping("/user/update")
	public ResponseEntity<ResponseDTO> update(@RequestBody(required = true) @Validated(ValidationGroups.Update.class) UtenteReq req)
			throws Exception{
		ResponseDTO r = new ResponseDTO();
		utS.update(req);
		r.setMsg(msgS.get("rest_updated"));
		return ResponseEntity.ok(r);			
	}
	
	@DeleteMapping("/admin/delete/{id}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable(required = true)  String id) throws Exception{
		ResponseDTO r = new ResponseDTO();
		utS.delete(id);
		r.setMsg(msgS.get("rest_deleted"));
		return ResponseEntity.ok(r);		
	}

	@GetMapping("/admin/list")
	public ResponseEntity<Object> list(
			@RequestParam (required = false)  String userName,
			@RequestParam (required = false)  String nome,
			@RequestParam (required = false)  String cognome,
			@RequestParam (required = false)  String role
			) throws Exception{

		return ResponseEntity.ok(utS.list(userName, nome, cognome, role));		
		
	}
	
	@GetMapping("/user/getById")
	public ResponseEntity<Object> getById(@RequestParam (required = true)  String userName) throws Exception{

		return ResponseEntity.ok(utS.getById(userName));		
		
	}

}
