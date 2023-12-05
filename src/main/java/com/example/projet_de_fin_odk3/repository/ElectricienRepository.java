package com.example.projet_de_fin_odk3.repository;

import com.example.projet_de_fin_odk3.model.Electricien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectricienRepository extends JpaRepository<Electricien,Integer> {

    Electricien findByIdElectricien(int idElectricien);
    Electricien findByEmail(String email);
    List<Electricien> findElectricienByActiveOrderByIdElectricienDesc(boolean active);
}
