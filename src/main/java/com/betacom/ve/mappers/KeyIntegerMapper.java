package com.betacom.ve.mappers;

import java.util.List;

import com.betacom.ve.dto.output.KeyIntegerDTO;
import com.betacom.ve.models.BaseKeyInteger;

public class KeyIntegerMapper {

	public static List<KeyIntegerDTO> keyIntegerToDTO (List<? extends BaseKeyInteger> lI){
		return lI.stream()
				.map(i ->  keyIntegerToDTO(i))
				.toList();
		
	}

	
	public static KeyIntegerDTO keyIntegerToDTO(BaseKeyInteger i) {
	    return KeyIntegerDTO.builder()
	            .id(i.getId())
	            .nome(i.getNome())
	            .build();
	}
}
