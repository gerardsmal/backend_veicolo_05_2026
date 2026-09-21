package com.betacom.ve.dto.input;

import jakarta.validation.constraints.NotBlank;
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

	@NotNull(
			groups = ValidationGroups.Create.class,
			message = "ordine_utente_invalid"
	)
	@NotBlank(
			groups = ValidationGroups.Create.class,
			message = "ordine_utente_invalid"
	)
	private String userName;

	private Integer spedizioneID;

	private Integer modalitaPagamentoID;
}
