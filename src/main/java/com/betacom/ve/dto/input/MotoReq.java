package com.betacom.ve.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MotoReq extends VeicoloReq{

	@NotBlank (groups = ValidationGroups.Create.class , message ="targa_invalid")
	@Pattern(
		    regexp = "^[A-Z]{2}[0-9]{5}$",
		    groups = {ValidationGroups.Create.class, ValidationGroups.Update.class},
		    message = "targa_invalid"
		)
	private String targa;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="cc_invalid")
	private Integer cc;

}
