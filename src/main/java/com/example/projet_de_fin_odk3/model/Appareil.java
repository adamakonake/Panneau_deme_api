package com.example.projet_de_fin_odk3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Appareil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAppareil;

    @NotBlank
    private String nom;

    @NotNull
    @Min(value = 1,message = "La puissance doit être superieure à 0")
    private int puissance;

    @NotNull
    @Min(value = 1,message = "HeureConso doit être superieure à 0")
    private int heureConso;

    @NotNull
    @Min(value = 1,message = "La quantité doit être superieure à 0")
    private int quantite;

    @ManyToOne()
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;
}
