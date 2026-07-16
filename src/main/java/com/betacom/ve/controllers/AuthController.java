package com.betacom.ve.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ve.dto.input.LoginReq;
import com.betacom.ve.dto.input.ValidationGroups;
import com.betacom.ve.services.interfaces.IUtenteServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/auth")
public class AuthController {
	
	private final IUtenteServices utS;
	
	@PostMapping("/login")
	public ResponseEntity<Object> me (@RequestBody(required = true)  @Validated(ValidationGroups.Create.class) LoginReq req) throws Exception{
		return ResponseEntity.ok(utS.login(req));
	}
}
