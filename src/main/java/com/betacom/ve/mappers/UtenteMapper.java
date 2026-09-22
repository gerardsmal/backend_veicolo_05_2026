package com.betacom.ve.mappers;

import java.util.List;

import com.betacom.ve.dto.output.UtenteDTO;
import com.betacom.ve.models.Utente;

public class UtenteMapper {

	public static List<UtenteDTO> utenteToDTO(List<Utente> utenti) {
		return utenti.stream()
				.map(UtenteMapper::utenteToDTO)
				.toList();
	}

	public static UtenteDTO utenteToDTO(Utente utente) {
		return UtenteDTO.builder()
				.userName(utente.getUserName())
				.nome(utente.getNome())
				.cognome(utente.getCognome())
				.via(utente.getVia())
				.comune(utente.getComune())
				.cap(utente.getCap())
				.email(utente.getEmail())
				.role(utente.getRole().toString())
				.sesso(utente.getSesso())
				.telefono(utente.getTelefono())
				.isValidate(utente.getValidate())
				.build();
	}
}
