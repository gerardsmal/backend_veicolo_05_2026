package com.betacom.ve.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ve.dto.input.KeyStringReq;
import com.betacom.ve.dto.input.ValidationGroups;
import com.betacom.ve.dto.output.ResponseDTO;
import com.betacom.ve.services.interfaces.ICategorieServices;
import com.betacom.ve.services.interfaces.IMessageServices;
import com.betacom.ve.services.interfaces.ITipoAlimentazioneServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping ("rest/alimentazione")
public class AlimentazioneControlle {

	private final ITipoAlimentazioneServices tipoS;
	private final IMessageServices  msgS;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) KeyStringReq req) 
			throws Exception{
		ResponseDTO r = new ResponseDTO();
		tipoS.create(req);
		r.setMsg(msgS.get("rest_created"));
		return ResponseEntity.ok(r);		
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable(required = true)  String id) throws Exception{
		ResponseDTO r = new ResponseDTO();
		tipoS.delete(id);
		r.setMsg(msgS.get("rest_deleted"));
		return ResponseEntity.ok(r);		
	}
	
	@GetMapping("/list")
	public ResponseEntity<Object> list(@RequestParam (required = false)  String pattern) throws Exception{
		return ResponseEntity.ok(tipoS.list(pattern));
	}
}
