package com.example.projet_de_fin_odk3.repository;

import com.example.projet_de_fin_odk3.model.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrateurRepository extends JpaRepository<Administrateur, Integer> {
    Administrateur findAdministrateurByEmail(String email);
    Administrateur findAdministrateurByIdAdministrateur(int idAdmin);
}
