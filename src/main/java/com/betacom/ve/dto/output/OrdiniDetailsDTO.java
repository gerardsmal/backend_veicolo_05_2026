package com.betacom.ve.dto.output;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class OrdiniDetailsDTO {

	private Integer id;
	private LocalDate dataCreazione;
	private Integer quantita;
	private String productName;
	private String image;
	private double prezzoUnit;
	private double prezzo;
}
