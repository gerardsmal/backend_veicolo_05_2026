package com.betacom.ve.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadServices {

	String saveImage(MultipartFile file, Integer id) throws Exception;

	String buildUrl(String filename);
}
