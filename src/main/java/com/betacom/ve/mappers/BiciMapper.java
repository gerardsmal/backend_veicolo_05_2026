package com.betacom.ve.mappers;

import com.betacom.ve.dto.output.BiciDTO;
import com.betacom.ve.models.Bici;

public class BiciMapper {
	
	public static BiciDTO builBiciDTO(Bici v) {
		return BiciDTO.builder()
				.id(v.getId())
				.pieghevole(v.getPieghevole())
				.sospenzione(KeyIntegerMapper.keyIntegerToDTO(v.getSospenzione()))
				.numeroMarce(v.getNumeroMarce())
				.build();								
	}
}
