package com.example.projet_de_fin_odk3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Electricien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idElectricien;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotNull
    private int experience;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String telephone;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @NotBlank
    private String photo;

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "idAdministrateur")
    private Administrateur administrateur;
}
