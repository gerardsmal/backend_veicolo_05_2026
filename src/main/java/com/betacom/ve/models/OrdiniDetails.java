package com.betacom.ve.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table (name = "ordini-detagli")
public class OrdiniDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="data_aggiunto_carrello")
    private LocalDate dataCreazione;

    private Integer quantita;
    private String productName;
    private String image;

    @Column(name="prezzo_unitatio")
    private double prezzoUnit;

    @Column(name="prezzo_da_pagare")
    private double prezzo;

    @ManyToOne
    @JoinColumn (name ="id_ordine")
    private Ordini ordini;
}
