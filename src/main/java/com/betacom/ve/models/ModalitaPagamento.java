package com.betacom.ve.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "modalita_pagamento")
public class ModalitaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name="tipo_pagamento",
            length = 40,
            nullable = false)
    private String tipo;

}
