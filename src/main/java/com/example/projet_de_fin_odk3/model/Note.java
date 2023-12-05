package com.example.projet_de_fin_odk3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int idNote;

    @NotNull
    private double valeur;

    @ManyToOne
    @JoinColumn(name = "idDimensionnement")
    private Dimensionnement dimensionnement;

    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "idElectricien")
    private Electricien electricien;
}
