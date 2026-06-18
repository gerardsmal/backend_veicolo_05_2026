package com.betacom.ve.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (
		name="moto",
		uniqueConstraints = {
				@UniqueConstraint(
					name="uk_targa_moto",
					columnNames = "targa"		
				)
			}

		)
public class Moto {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column (unique = true,
			nullable = false)
	private String targa;
	
	@Column (nullable = false)
	private Integer cc;

	@OneToOne (cascade = CascadeType.REMOVE)
	@JoinColumn(
			name="veicolo_id",
			referencedColumnName = "id"
			)
	private Veicolo veicolo;

}