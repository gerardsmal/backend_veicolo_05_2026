package com.betacom.ve.dto.output;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class OrdiniDTO {

	private Integer id;
	private LocalDate dataOrdine;
	private LocalDate dataInvio;
	private String statusPagamento;
	private Double totale;
	private String numeroOrdine;
	private Integer modalitaPagamentoID;
	private String tipoPagamento;
	private SpedizioneDTO spedizione;
	private String userName;
	private List<OrdiniDetailsDTO> ordiniDetails;
}
