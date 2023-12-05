package com.example.projet_de_fin_odk3.repository;

import com.example.projet_de_fin_odk3.model.Dimensionnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DimensionnementRepository extends JpaRepository<Dimensionnement,Integer> {

    Dimensionnement findByIdDimensionnement(int id);
    List<Dimensionnement> findByUtilisateurIdUtilisateurOrderByDateDesc(int idUser);
    Dimensionnement findByDescription(String desc);
}
