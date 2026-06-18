package com.betacom.ve.dto.input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BiciReq extends VeicoloReq{

	@NotNull (groups = ValidationGroups.Create.class , message ="marce_invalid")
	@Min(value = 1, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}, message = "marce_invalid")
	@Max(value = 25, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}, message = "marce_invalid")
	private Integer numeroMarce;
	
	private Boolean pieghevole;

	@NotNull (groups = ValidationGroups.Create.class , message ="sos_invalid")		
	private Integer sospenzione;

}
