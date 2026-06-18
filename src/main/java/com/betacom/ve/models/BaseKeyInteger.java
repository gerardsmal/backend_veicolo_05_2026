package com.betacom.ve.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class BaseKeyInteger {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column (nullable = false, length = 50)
	private String nome;
}
