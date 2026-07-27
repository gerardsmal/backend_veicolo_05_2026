package com.betacom.ve.dto.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CarelloReq {

	private Integer id;
	@NotNull (groups = ValidationGroups.Create.class , message ="user_ntfnd")
	private String utenteID;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="veicolo_ntfnd")
	private Integer veicoloID;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="veicolo_ntfnd")
	@Min( value = 1, groups = { ValidationGroups.Create.class, ValidationGroups.Update.class},message = "carello_quantita_ko")
	private Integer quantita;
}
