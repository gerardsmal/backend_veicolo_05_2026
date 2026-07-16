package com.betacom.ve.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LoginReq {
	@NotNull (groups = ValidationGroups.Create.class , message ="login_invalid")
	private String userName;
	@NotNull (groups = ValidationGroups.Create.class , message ="login_invalid")
	private String pwd;

}
