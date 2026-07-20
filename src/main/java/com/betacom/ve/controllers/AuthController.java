package com.betacom.ve.controllers;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ve.dto.input.LoginReq;
import com.betacom.ve.dto.input.ValidationGroups;
import com.betacom.ve.dto.output.LoginDTO;
import com.betacom.ve.security.interfaces.JwtServices;
import com.betacom.ve.services.interfaces.IUtenteServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("rest/auth")
public class AuthController {

	private final IUtenteServices utS;
	private final AuthenticationManager authenticationManager;
	private final JwtServices jwtService;

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody @Validated(ValidationGroups.Create.class) LoginReq request)
			throws Exception {
		Object r = new Object();
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPwd()));

		String token = jwtService.generateAccessToken(authentication);
		String refreshToken = jwtService.generateRefreshToken(authentication);
		
		// generate cookie
	    ResponseCookie refreshCookie = ResponseCookie
	            .from("refreshToken", refreshToken)
	            .httpOnly(true)
	            .secure(false) // true in produzione con HTTPS
	            .sameSite("Lax")
	            .path("/rest/auth")
	            .maxAge(Duration.ofDays(7))
	            .build();

		r = LoginDTO.builder()
				.accessToken(token)
				.tokenType("Bearer")
				.build();

	    return ResponseEntity.ok()
	            .header(HttpHeaders.SET_COOKIE,refreshCookie.toString())
	            .body(r);

	}

	@GetMapping("/me")
	public ResponseEntity<Object> me(Authentication authentication) throws Exception {
		LoginReq req = new LoginReq();
		req.setUserName(authentication.getName());
		return ResponseEntity.ok(utS.me(req));
	}
}
