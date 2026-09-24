package com.betacom.ve.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ve.dto.input.OrdiniReq;
import com.betacom.ve.dto.input.SpedizioneReq;
import com.betacom.ve.dto.input.ValidationGroups;
import com.betacom.ve.dto.output.ModalitaPagamentoDTO;
import com.betacom.ve.dto.output.OrdiniDTO;
import com.betacom.ve.dto.output.ResponseDTO;
import com.betacom.ve.dto.output.SpedizioneDTO;
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

	@GetMapping("/user/listSpedizione")
	public ResponseEntity<List<SpedizioneDTO>> listSpedizione(Authentication authentication) throws Exception {
		return ResponseEntity.ok(ordineS.listSpedizione(authentication.getName()));
	}

	@PostMapping("/user/createSpedizione")
	public ResponseEntity<ResponseDTO> createSpedizione(Authentication authentication,
			@RequestBody(required = true) @Validated(ValidationGroups.Create.class) SpedizioneReq req) throws Exception {
		ordineS.createSpedizione(authentication.getName(), req);
		ResponseDTO response = new ResponseDTO();
		response.setMsg(msgS.get("rest_created"));
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/user/removeSpedizione/{id}")
	public ResponseEntity<ResponseDTO> removeSpedizione(Authentication authentication,
			@PathVariable(required = true) Integer id) throws Exception {
		ordineS.removeSpedizione(authentication.getName(), id);
		ResponseDTO response = new ResponseDTO();
		response.setMsg(msgS.get("rest_deleted"));
		return ResponseEntity.ok(response);
	}

	@PutMapping("/user/assignSpedizione")
	public ResponseEntity<ResponseDTO> assignSpedizione(Authentication authentication,
			@RequestBody(required = true) @Validated(ValidationGroups.Update.class) OrdiniReq req) throws Exception {
		ordineS.assignSpedizione(authentication.getName(), req);
		ResponseDTO response = new ResponseDTO();
		response.setMsg(msgS.get("rest_updated"));
		return ResponseEntity.ok(response);
	}

	@PutMapping("/user/assignModalitaPagamento")
	public ResponseEntity<ResponseDTO> assignModalitaPagamento(Authentication authentication,
			@RequestBody(required = true) @Validated(ValidationGroups.Update.class) OrdiniReq req) throws Exception {
		ordineS.assignModalitaPagamento(authentication.getName(), req);
		ResponseDTO response = new ResponseDTO();
		response.setMsg(msgS.get("rest_updated"));
		return ResponseEntity.ok(response);
	}

	@GetMapping("/public/listModalitaPagamento")
	public ResponseEntity<List<ModalitaPagamentoDTO>> listModalitaPagamento() throws Exception {
		return ResponseEntity.ok(ordineS.listModalitaPagamento());
	}

	@PutMapping("/user/confermo")
	public ResponseEntity<OrdiniDTO> confermo(Authentication authentication) throws Exception {
		return ResponseEntity.ok(ordineS.confermo(authentication.getName()));
	}

	@DeleteMapping("/user/remove")
	public ResponseEntity<ResponseDTO> remove(Authentication authentication) throws Exception {
		ordineS.remove(authentication.getName());
		ResponseDTO response = new ResponseDTO();
		response.setMsg(msgS.get("rest_deleted"));
		return ResponseEntity.ok(response);
	}
}
