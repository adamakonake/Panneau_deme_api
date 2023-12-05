package com.example.projet_de_fin_odk3.repository;

import com.example.projet_de_fin_odk3.model.ListEqui;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListEquiRepository extends JpaRepository<ListEqui, Integer> {

    List<ListEqui> findByDimensionnementIdDimensionnement(int idDim);
}
