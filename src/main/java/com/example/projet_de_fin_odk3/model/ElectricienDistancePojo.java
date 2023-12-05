package com.example.projet_de_fin_odk3.model;

import lombok.Data;

@Data
public class ElectricienDistancePojo implements Comparable<ElectricienDistancePojo>  {

    private String distance;

    private Electricien electricien;

    @Override
    public int compareTo(ElectricienDistancePojo o) {
        return this.getDistance().compareTo(o.getDistance());
    }
}
