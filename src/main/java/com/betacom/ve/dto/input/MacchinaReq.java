package com.betacom.ve.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)

public class MacchinaReq extends VeicoloReq{

	//private Integer idMacchina;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="porte_invalid")
	private Integer numeroPorte;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="targa_invalid")
	@NotBlank (groups = ValidationGroups.Create.class , message ="targa_invalid")
	@Pattern(
		    regexp = "^[A-Z]{2}[0-9]{3}[A-Z]{2}$",
		    groups = {ValidationGroups.Create.class, ValidationGroups.Update.class},
		    message = "targa_invalid"
		)
	private String targa;

	
	@NotNull (groups = ValidationGroups.Create.class , message ="cc_invalid")
	private Integer cc;

}