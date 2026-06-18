package com.betacom.ve.models;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table (name="tipo_sospenzione")
public class Sospenzione extends BaseKeyInteger{

	@OneToMany(
			mappedBy = "sospenzione",
			fetch = FetchType.LAZY
			)
	private List<Bici> bici;
}