package com.betacom.ve.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KeyStringReq {

	@NotNull (groups = ValidationGroups.Create.class , message ="key_invalid")
	private String key;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="value_invalid")
	private String value;
}
