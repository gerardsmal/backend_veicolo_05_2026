package com.betacom.ve.dto.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class SpedizioneDTO {

	private Integer id;
	private Boolean predefinito;
	private String nome;
	private String cognome;
	private String via;
	private String commune;
	private String cap;
	private String utenteID;
}
