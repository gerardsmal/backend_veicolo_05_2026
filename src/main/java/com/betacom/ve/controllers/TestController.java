package com.betacom.ve.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/test")
public class TestController {

	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}
}
