package com.example.projet_de_fin_odk3.repository;

import com.example.projet_de_fin_odk3.model.Equipement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipementRepository extends JpaRepository<Equipement,Integer> {
    Equipement findEquipementByIdEquipement(int idEquipement);
    Equipement findEquipementByNomAndTypeEquipementTitreAndMarque(String nom,String titre,String marque);
    Equipement findFirstByPuissanceIsLessThanEqualAndTypeEquipementTitreOrderByPuissanceDesc(int puissance,String titre); //pour panneau
    List<Equipement> findByPuissanceAndTypeEquipementTitre(int puissance, String titre); //liste pour le dimensionnement
    Equipement findFirstByPuissanceIsGreaterThanEqualAndTypeEquipementTitreOrderByPuissanceAsc(int puissance,String titre); // pour ondulaire
    Equipement findFirstByPuissanceIsGreaterThanEqualAndTensionEqualsAndTypeEquipementTitreOrderByPuissanceAsc(int puissance, int tension, String titre); // pour ondulaire
    List<Equipement> findByTensionAndIntensiteAndTypeEquipementTitre(int tension, int intensite, String titre); //pour batterie
    List<Equipement> findByPuissanceAndTensionAndTypeEquipementTitre(int puissance, int tension, String titre);


}
