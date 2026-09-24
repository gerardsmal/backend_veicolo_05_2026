package com.betacom.ve.dto.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ModalitaPagamentoDTO {

	private Integer id;
	private String tipo;
}
