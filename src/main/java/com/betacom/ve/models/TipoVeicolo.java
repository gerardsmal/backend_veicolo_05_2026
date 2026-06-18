package com.betacom.ve.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name="tipo_veicolo")
public class TipoVeicolo {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column (nullable = false, length = 50)
	private String nome;
	
	@Column (nullable = false, length = 50)
	private String pattern;
	
	private boolean status;
}
