package com.betacom.ve.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ve.dto.output.ResponseDTO;
import com.betacom.ve.services.interfaces.IMessageServices;
import com.betacom.ve.services.interfaces.IOrdineServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/ordini")
public class OrdiniController {

	private final IOrdineServices ordineS;
	private final IMessageServices msgS;

	@PostMapping("/user/create")
	public ResponseEntity<ResponseDTO> create(Authentication authentication) throws Exception {
		ResponseDTO response = new ResponseDTO();
		ordineS.create(authentication.getName());
		response.setMsg(msgS.get("rest_created"));
		return ResponseEntity.ok(response);
	}
}
