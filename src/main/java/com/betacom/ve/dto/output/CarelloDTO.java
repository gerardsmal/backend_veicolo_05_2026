package com.betacom.ve.dto.output;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CarelloDTO {
	private String userName;
	private String nome;
	private String cognome;
	
	private Integer id;
	private String status;
	private double prezzoTotale;
	
	List<CarelloDetaglioDTO> carelloDetaglio;
}
