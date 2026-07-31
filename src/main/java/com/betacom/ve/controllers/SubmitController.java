package com.betacom.ve.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ve.dto.output.ResponseDTO;
import com.betacom.ve.services.interfaces.IMessageServices;
import com.betacom.ve.services.interfaces.ISubmitServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping ("rest/submit")
public class SubmitController {

	private final ISubmitServices exec;
	private final IMessageServices msgS;
	
	
	@GetMapping("/public/run")
	public ResponseEntity<ResponseDTO> run() throws Exception{
		exec.run();
		ResponseDTO r = new ResponseDTO();
		r.setMsg(msgS.get("batch_ok"));
		return ResponseEntity.ok(r);
	}
}
