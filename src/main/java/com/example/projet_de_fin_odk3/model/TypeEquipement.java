package com.example.projet_de_fin_odk3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class TypeEquipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTypeEquipement;

    @NotBlank
    private String titre;

    @ManyToOne
    @JoinColumn(name = "idAdmin")
    private Administrateur administrateur;
}
