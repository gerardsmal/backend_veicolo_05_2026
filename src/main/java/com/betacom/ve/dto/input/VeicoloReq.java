package com.betacom.ve.dto.input;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VeicoloReq {	
	private Integer id;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="ruote_invalid")
	@Min(value = 2, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}, message = "ruote_invalid")
	@Max(value = 6, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}, message = "ruote_invalid")
	private Integer numeroRuote;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="model_invalid")
	@NotBlank (groups = ValidationGroups.Create.class , message ="model_invalid")
	private String  modello;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="anno_invalid")
	private Integer annoProduzione;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="tipo_veicolo_invalid")
	private Integer tipoVeicolo;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="cat_invalid")
	private String categorie;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="alim_invalid")
	private String tipoAlimentazione;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="colore_invalid")
	private Integer colore;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="marca_invalid")
	private Integer marca;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="prezzo_invalid")
	private Double prezzo;
}
