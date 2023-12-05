package com.example.projet_de_fin_odk3.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ListEqui {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idListEqui;

    private int quantite;

    @ManyToOne
    @JoinColumn(name = "idDimensionnement")
    private Dimensionnement dimensionnement;

    @ManyToOne
    @JoinColumn(name = "idEquipement")
    private Equipement equipement;
}
