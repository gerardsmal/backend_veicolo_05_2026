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
@Table (name="colore")
public class Colore extends BaseKeyInteger{
	
	@OneToMany(
			mappedBy = "colore",
			fetch = FetchType.LAZY
			)
	private List<Veicolo> veicolos;

}