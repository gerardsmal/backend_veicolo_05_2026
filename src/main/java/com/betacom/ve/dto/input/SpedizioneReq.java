package com.betacom.ve.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SpedizioneReq {

	@NotNull(groups = ValidationGroups.Update.class, message = "spedizione_id_invalid")
	private Integer id;

	@NotNull(groups = ValidationGroups.Create.class, message = "spedizione_predefinito_invalid")
	private Boolean predefinito;

	@NotNull(groups = ValidationGroups.Create.class, message = "spedizione_nome_invalid")
	@NotBlank(groups = ValidationGroups.Create.class, message = "spedizione_nome_invalid")
	@Size(max = 100, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}, message = "spedizione_nome_invalid")
	private String nome;

	@NotNull(groups = ValidationGroups.Create.class, message = "spedizione_cognome_invalid")
	@NotBlank(groups = ValidationGroups.Create.class, message = "spedizione_cognome_invalid")
	@Size(max = 100, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}, message = "spedizione_cognome_invalid")
	private String cognome;

	@NotNull(groups = ValidationGroups.Create.class, message = "spedizione_via_invalid")
	@NotBlank(groups = ValidationGroups.Create.class, message = "spedizione_via_invalid")
	@Size(max = 100, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}, message = "spedizione_via_invalid")
	private String via;

	@NotNull(groups = ValidationGroups.Create.class, message = "spedizione_commune_invalid")
	@NotBlank(groups = ValidationGroups.Create.class, message = "spedizione_commune_invalid")
	@Size(max = 100, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}, message = "spedizione_commune_invalid")
	private String commune;

	@NotNull(groups = ValidationGroups.Create.class, message = "spedizione_cap_invalid")
	@Pattern(
			regexp = "^\\d{5}$",
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class},
			message = "spedizione_cap_invalid"
	)
	private String cap;

	@NotNull(groups = ValidationGroups.Create.class, message = "spedizione_utente_invalid")
	@NotBlank(groups = ValidationGroups.Create.class, message = "spedizione_utente_invalid")
	private String utenteID;
}
