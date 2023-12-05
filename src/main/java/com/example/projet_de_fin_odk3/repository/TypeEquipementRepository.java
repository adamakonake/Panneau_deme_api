package com.example.projet_de_fin_odk3.repository;

import com.example.projet_de_fin_odk3.model.TypeEquipement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeEquipementRepository extends JpaRepository<TypeEquipement,Integer> {
    TypeEquipement findTypeEquipementByIdTypeEquipement(int id);
    TypeEquipement findTypeEquipementByTitre(String titre);
}
