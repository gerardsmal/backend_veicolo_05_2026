package com.betacom.ve.mappers;

import java.util.List;

import com.betacom.ve.dto.output.TipoVeicoloDTO;
import com.betacom.ve.models.TipoVeicolo;

public class TipoVeicoloMapper {
	public static List<TipoVeicoloDTO> tipoVeicoloToDTO(List<TipoVeicolo> lT){
		return lT.stream()
				.map(t -> tipoVeicoloToDTO(t)
						).toList();		
	}
	
	public static TipoVeicoloDTO tipoVeicoloToDTO(TipoVeicolo t) {
		return TipoVeicoloDTO.builder()
				.id(t.getId())
				.nome(t.getNome())
				.pattern(t.getPattern())
				.build();
	}
}
