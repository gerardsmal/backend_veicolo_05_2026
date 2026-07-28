package com.betacom.ve.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ve.dto.input.MailReq;
import com.betacom.ve.dto.output.ResponseDTO;
import com.betacom.ve.services.interfaces.IMailServices;
import com.betacom.ve.services.interfaces.IMessageServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping ("rest/mail")
public class MailController {
	
	private final IMailServices mailS;
	private final IMessageServices   msgS;
	
	@PostMapping("/send")
	public ResponseEntity<ResponseDTO> send(@RequestBody (required = true) MailReq req) throws Exception{
		ResponseDTO r = new ResponseDTO(); 
			mailS.sendMail(req);
			r.setMsg(msgS.get("rest_created"));
		return ResponseEntity.ok(r);
	}
	
}
