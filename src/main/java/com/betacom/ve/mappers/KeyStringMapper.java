package com.betacom.ve.mappers;

import java.util.List;

import com.betacom.ve.dto.output.KeyStringDTO;
import com.betacom.ve.models.BaseKeyString;

public class KeyStringMapper {
	public static List<KeyStringDTO> keyStringToDTO (List<? extends BaseKeyString> lI){
		return lI.stream()
				.map(i ->  keyStringToDTO(i))
				.toList();
		
	}
	
	public static KeyStringDTO keyStringToDTO(BaseKeyString i) {
	    return KeyStringDTO.builder()
	            .id(i.getId())
	            .nome(i.getNome())
	            .build();
	}
}
