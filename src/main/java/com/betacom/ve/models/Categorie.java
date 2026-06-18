package com.betacom.ve.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name="categorie_veicolo")
public class Categorie extends BaseKeyString{

	@OneToMany(
			mappedBy = "categorie",
			fetch = FetchType.LAZY
			)
	private List<Veicolo> veicolos;

}
