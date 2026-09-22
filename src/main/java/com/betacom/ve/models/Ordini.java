package com.betacom.ve.models;

import com.betacom.ve.enums.StatoPagamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table (name = "ordini")
public class Ordini {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="data_ordine")
    private LocalDate dataOrdine;

    @Column(name="data_invio")
    private LocalDate dataInvio;

    @Column (name="stato_pagamenti")
    private StatoPagamento statusPagamento;

    @Column (name="totale_ordine")
    private Double totale;

    @Column (name="numero_ordine",
            unique=true)
    private String numeroOrdine;

    @ManyToOne
    @JoinColumn (name="id_modalita")
    private ModalitaPagamento modalitaPagamento;

    @ManyToOne
    @JoinColumn (name="id_spedizione")
    private Spedizione spedizione;

    @ManyToOne
    @JoinColumn (name="id_utente")
    private Utente utente;

    @OneToMany(
            mappedBy = "ordini",
            cascade = CascadeType.REMOVE, orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<OrdiniDetails> ordiniDetails;

}
