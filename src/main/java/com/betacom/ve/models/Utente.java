package com.betacom.ve.models;

import java.time.LocalDate;
import java.util.List;

import com.betacom.ve.enums.Roles;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name="utente")
public class Utente {
	@Id
	private String userName;
	private String pwd;
	private Roles  role;
	private String nome;
	private String cognome;
	private Boolean sesso;
	private String email;
	private String telefono;
	private String via;
	private String comune;
	private String cap;

	@Column (name="email_validation",
			nullable= false,
			columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean validate;


	@Column (name="data_creazione")
	private LocalDate dataCreazione;

	@OneToOne (
			mappedBy = "utente",
			cascade = CascadeType.REMOVE
	)
	private Carello carello;

	@OneToMany(
			mappedBy = "utente",
			fetch = FetchType.EAGER
	)
	private List<Spedizione> spedizione;

}
