package com.betacom.ve.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangePwdReq {
	String userName;
	@NotNull (groups = ValidationGroups.Update.class , message ="login_invalid")
	String oldPwd;
	@NotNull (groups = ValidationGroups.Create.class , message ="user_invalid_format_pwd")
	@Pattern(
			groups = {ValidationGroups.Create.class,ValidationGroups.Update.class},
			regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.#_\\-])[A-Za-z\\d@$!%*?&.#_\\-]{8,}$",
			message = "user_invalid_format_pwd"
			)
	String newPwd;
}
