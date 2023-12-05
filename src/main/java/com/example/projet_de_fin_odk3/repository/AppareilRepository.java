package com.example.projet_de_fin_odk3.repository;

import com.example.projet_de_fin_odk3.model.Appareil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppareilRepository extends JpaRepository<Appareil,Integer> {
    Appareil findByIdAppareil(int idAppareil);
    Appareil findByNomAndUtilisateurIdUtilisateur(String nom,int idUser);
    List<Appareil> findByUtilisateurIdUtilisateur(int idUser);
}
