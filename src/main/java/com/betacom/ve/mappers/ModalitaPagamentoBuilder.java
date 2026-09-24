package com.betacom.ve.mappers;

import java.util.List;

import com.betacom.ve.dto.output.ModalitaPagamentoDTO;
import com.betacom.ve.models.ModalitaPagamento;

public class ModalitaPagamentoBuilder {

	public static ModalitaPagamentoDTO modalitaPagamentoToDTO(ModalitaPagamento modalitaPagamento) {
		return ModalitaPagamentoDTO.builder()
				.id(modalitaPagamento.getId())
				.tipo(modalitaPagamento.getTipo())
				.build();
	}

	public static List<ModalitaPagamentoDTO> modalitaPagamentoToDTO(List<ModalitaPagamento> modalitaPagamento) {
		return modalitaPagamento.stream()
				.map(ModalitaPagamentoBuilder::modalitaPagamentoToDTO)
				.toList();
	}
}
