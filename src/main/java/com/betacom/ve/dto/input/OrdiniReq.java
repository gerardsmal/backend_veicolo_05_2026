package com.betacom.ve.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrdiniReq {

	@NotNull(
			groups = ValidationGroups.Update.class,
			message = "ordine_id_invalid"
	)
	private Integer id;

	private String userName;

	private Integer spedizioneID;

	private Integer modalitaPagamentoID;
}
