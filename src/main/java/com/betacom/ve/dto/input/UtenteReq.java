package com.betacom.ve.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UtenteReq {
	
	@NotNull (groups = {ValidationGroups.Create.class} , message ="user_incomplete")
	private String userName;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="user_incomplete")
	@Pattern(
		groups = {ValidationGroups.Create.class,ValidationGroups.Update.class},
		regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.#_\\-])[A-Za-z\\d@$!%*?&.#_\\-]{8,}$",
		message = "user_invalid_format_pwd"
		)
	private String pwd;	
	
	@NotNull (groups = ValidationGroups.Create.class , message ="user_incomplete")
	private String nome;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="user_incomplete")
	private String cognome;
	
//	@Pattern(
//		    regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
//		    message = "user_invalid_email"
//		)
	private String email;	
	
	@NotNull (groups = ValidationGroups.Create.class , message ="user_incomplete")
	private Boolean sesso; // true mas. false fem)
	
	private String telefono;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="user_incomplete")
	private String via;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="user_incomplete")
	private String commune;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="user_incomplete")
	@Pattern(
			groups = {ValidationGroups.Create.class,ValidationGroups.Update.class},
		    regexp = "^\\d{5}$",
		    message = "user_invalid_cap"
		)
	private String cap;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="user_incomplete")
	@Pattern(
			groups = {ValidationGroups.Create.class,ValidationGroups.Update.class},
		    regexp = "^(ADMIN|USER)$",
		    message = "user_invalid_role"
		)
	private String role;
}
