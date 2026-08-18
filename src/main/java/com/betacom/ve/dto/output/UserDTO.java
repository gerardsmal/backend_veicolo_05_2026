package com.betacom.ve.dto.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {
	private Long  id;
	private String userName;
	private String keycloakSubject;
	private String nome;
	private String cognome;
	private Boolean sesso;
	private String email;
	private String telefono;
	private String via;
	private String comune;
	private String cap;
	private Boolean isValidate;
	
}
