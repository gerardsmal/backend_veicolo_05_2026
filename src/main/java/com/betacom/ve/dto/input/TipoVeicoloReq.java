package com.betacom.ve.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TipoVeicoloReq {

	private Integer id;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="value_invalid")
	@NotBlank (groups = ValidationGroups.Create.class , message ="value_invalid")
	private String nome;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="pattern_invalid")
	@NotBlank (groups = ValidationGroups.Create.class , message ="pattern_invalid")	
	private String pattern;

	private Boolean status;
}
