package com.betacom.ve.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "contatori")
public class Contatore {
	@Id
	@Column(name = "tipo", length = 40)
	private String tipo;

	@Column(name = "anno", nullable = false)
	private Integer anno;

	@Column(name = "ultimo_numero", nullable = false)
	private Long ultimoNumero;
}
