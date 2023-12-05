package com.example.projet_de_fin_odk3.repository;

import com.example.projet_de_fin_odk3.model.ListApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListAppRepository extends JpaRepository<ListApp,Integer> {

    List<ListApp> findByDimensionnementIdDimensionnement(int idDim);
    List<ListApp> findByAppareilIdAppareil(int idAppareil);
}
