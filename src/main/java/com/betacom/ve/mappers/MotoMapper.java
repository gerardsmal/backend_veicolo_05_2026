package com.betacom.ve.mappers;

import com.betacom.ve.dto.output.MotoDTO;
import com.betacom.ve.models.Moto;

public class MotoMapper {

	public static MotoDTO builMotoDTO(Moto v) {
		return MotoDTO.builder()
				.id(v.getId())
				.cc(v.getCc())
				.targa(v.getTarga())
				.build();								
	}
}
