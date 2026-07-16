package com.betacom.ve.services.implementations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;

import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.models.Veicolo;
import com.betacom.ve.repositories.IVeicoloRepository;
import com.betacom.ve.services.interfaces.IUploadServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UploadImpl implements IUploadServices{
	private final Path uploadPath;
	private final IVeicoloRepository veiR;
	
	public UploadImpl(@Value("${app.upload.dir:uploads}") String uploadDir,  // valore per default della value
			IVeicoloRepository veiR ) {
	        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize(); // transform relative path in absolute  path
	        this.veiR = veiR;
	        init();
	    }	
	
	private void init() {
		try {
			if (Files.notExists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
		} catch (IOException e) {
			throw new RuntimeException("upload_create");
		}
	}
	
	@Override
	public String saveImage(MultipartFile file, Integer id) throws Exception {
		log.debug("saveImage {}", id);
		
		Assert.isTrue(!file.isEmpty(),() -> "upload_empty"); // control file loaded lancia l'exception in caso file empty

		String uniqueName = buildFileName(file);
		Path destinationFile = uploadPath.resolve(uniqueName);
		 
		try {
			Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
			Veicolo v = veiR.findById(id)
					.orElseThrow(() -> new AcademyException("veicolo_ntfnd"));	
			v.setImage(uniqueName);
			veiR.save(v);
		} catch (IOException e) {
	            throw new AcademyException("upload_save_error");
		}
		return uniqueName;
	}
	/*
	 * Normalize internal file name
	 */
	private String buildFileName(MultipartFile file) {
		String original = file.getOriginalFilename();
        String extension = "";
        String originalName = original.trim().replaceAll("\\s+", "_"); // normalize file name
 
        log.debug("originalName: {}" , originalName);
        
        extension = Optional.ofNullable(originalName)         // search extension file 
                .filter(name -> name.contains("."))
                .map(name -> name.substring(name.lastIndexOf(".")))
                .orElse("");

        // Build unique name
        return originalName.substring(0, originalName.lastIndexOf(".")) + "-" +  UUID.randomUUID().toString() + extension;

	}


	@Override
	public String buildUrl(String filename) {
		return ServletUriComponentsBuilder.fromCurrentContextPath()  // recupera la parte iniziale dell URL // localhost:8080/
                .path("/images/")    // il prefisse sarebbe image
                .path(filename)                 // il nome del file
                .toUriString();
	}

}
