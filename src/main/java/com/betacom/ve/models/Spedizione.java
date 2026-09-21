package com.betacom.ve.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "anagrafica_spedizione")
public class Spedizione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean predefinito;

    @Column (name="nome",
            nullable = false,
            length = 100)
    private String nome;

    @Column (name="cognome",
            nullable = false,
            length = 100)
    private String cognome;

    @Column (name="via",
            nullable = false,
            length = 100)
    private String via;

    @Column (name="commune",
            nullable = false,
            length = 100)
    private String commune;

    @Column (name="cap",
            nullable = false,
            length = 5)
    private String cap;

    @ManyToOne
    @JoinColumn (name = "id_utente")
    private Utente utente;

}
