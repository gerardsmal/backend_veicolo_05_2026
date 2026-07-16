package com.betacom.ve.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.betacom.ve.dto.output.ResponseDTO;
import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.services.interfaces.IMessageServices;
import com.betacom.ve.services.interfaces.IUploadServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/upload")
public class UploadController {
	
	private final IUploadServices uplS;
	private final IMessageServices msgS;
	
	@PostMapping(value = "/admin/image", consumes = "multipart/form-data")
	public ResponseEntity<ResponseDTO> uploadImage(
			@RequestParam MultipartFile file,
			@RequestParam Integer id) throws Exception {
		
		ResponseDTO r = new ResponseDTO();	 
		//  Test del content type: PNG, JPG GIF, ...
		if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
			throw new AcademyException("upload_invalid");
		}	 
		
		r.setMsg(uplS.saveImage(file, id));
		return ResponseEntity.ok(r);
			 
	 }

	@GetMapping("/admin/getUrl")
	public ResponseEntity<ResponseDTO> getUrl(@RequestParam (required = true) String filename) 
			throws  Exception{
		ResponseDTO r = new ResponseDTO();
		r.setMsg(uplS.buildUrl(filename));
		return ResponseEntity.ok(r);
	}

}
