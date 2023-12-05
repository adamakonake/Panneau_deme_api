package com.example.projet_de_fin_odk3.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Administrateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAdministrateur;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotBlank
    @Email
    private String email;

    private String motDePasse;

    @NotBlank
    private String telephone;

    @NotNull
    private boolean superAdmin;

    private boolean active = true;

    private String photo;

    @ManyToOne
    @JoinColumn(name = "idSuperAdmin")
    private Administrateur superAdministrateur;


}
