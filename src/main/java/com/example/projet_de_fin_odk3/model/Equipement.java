package com.example.projet_de_fin_odk3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEquipement;

    @NotBlank
    private String nom;

    @NotBlank
    private String marque;

    @Column(columnDefinition = "integer default 0")
    private int puissance;

    @Column(columnDefinition = "integer default 0")
    private int tension;

    @Column(columnDefinition = "integer default 0")
    private int intensite;

    @NotNull
    private int prix;

    @ManyToOne
    @JoinColumn(name = "idType")
    private TypeEquipement typeEquipement;

    @ManyToOne
    @JoinColumn(name = "idAdministrateur")
    private Administrateur administrateur;
}
