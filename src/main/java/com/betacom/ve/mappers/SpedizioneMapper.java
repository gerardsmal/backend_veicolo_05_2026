package com.betacom.ve.mappers;

import java.util.List;

import com.betacom.ve.dto.output.SpedizioneDTO;
import com.betacom.ve.models.Spedizione;

public class SpedizioneMapper {

	public static SpedizioneDTO spedizioneToDTO(Spedizione spedizione) {
		return SpedizioneDTO.builder()
				.id(spedizione.getId())
				.predefinito(spedizione.getPredefinito())
				.nome(spedizione.getNome())
				.cognome(spedizione.getCognome())
				.via(spedizione.getVia())
				.commune(spedizione.getCommune())
				.cap(spedizione.getCap())
				.utenteID(spedizione.getUtente().getUserName())
				.build();
	}

	public static List<SpedizioneDTO> spedizioneToDTO(List<Spedizione> spedizioni) {
		if (spedizioni == null)
			return List.of();

		return spedizioni.stream()
				.map(SpedizioneMapper::spedizioneToDTO)
				.toList();
	}
}
