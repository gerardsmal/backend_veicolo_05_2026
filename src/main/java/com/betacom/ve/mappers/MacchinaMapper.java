package com.betacom.ve.mappers;

import com.betacom.ve.dto.output.MacchinaDTO;
import com.betacom.ve.models.Macchina;

public class MacchinaMapper {
	public static MacchinaDTO builMacchinaDTO(Macchina v) {
		return MacchinaDTO.builder()
				.id(v.getId())
				.cc(v.getCc())
				.targa(v.getTarga())
				.numeroPorte(v.getNumeroPorte())
				.build();								
	}
}
