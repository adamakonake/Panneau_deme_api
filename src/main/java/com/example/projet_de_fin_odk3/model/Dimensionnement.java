package com.example.projet_de_fin_odk3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Dimensionnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDimensionnement;

    @Lob
    @NotBlank
    private String description;

    private int puissanceCrete;

    private int capaciteBatterie;

    private int puissanceRegulateur;

    private int puissanceOnduleur;

    private int tensionTravail;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "idElectricien")
    private Electricien electricien;

    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;
}
