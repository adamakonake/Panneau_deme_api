package com.example.projet_de_fin_odk3.repository;

import com.example.projet_de_fin_odk3.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {

    Utilisateur findByEmail(String email);
    Utilisateur findByIdUtilisateur(int idUser);
}
