package com.example.projet_de_fin_odk3.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ListApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idListApp;

    @ManyToOne
    @JoinColumn(name = "idAppareil")
    private Appareil appareil;

    @ManyToOne
    @JoinColumn(name = "idDimensionnement")
    private Dimensionnement dimensionnement;

}
