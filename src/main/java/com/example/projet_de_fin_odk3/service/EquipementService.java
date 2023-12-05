package com.example.projet_de_fin_odk3.service;

import com.example.projet_de_fin_odk3.exception.AlreadyExistException;
import com.example.projet_de_fin_odk3.exception.NotFoundException;
import com.example.projet_de_fin_odk3.model.Equipement;
import com.example.projet_de_fin_odk3.repository.EquipementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipementService {

    @Autowired
    private EquipementRepository equipementRepository;

    //Fonction pour ajouter un equipement
    public Equipement addEquipement(Equipement equipement){
        Equipement equipementVerif = equipementRepository.findEquipementByNomAndTypeEquipementTitreAndMarque(equipement.getNom(),equipement.getTypeEquipement().getTitre(),equipement.getMarque());
        if (equipementVerif != null)
            throw new AlreadyExistException("exist");
        return equipementRepository.save(equipement);
    }

    //Fonction pour obtenir la liste des équipement
    public List<Equipement> getAllEquipement(){
        List<Equipement> list = equipementRepository.findAll(Sort.by(Sort.Direction.DESC, "idEquipement"));
        if (list.isEmpty())
            throw new NotFoundException("empty");
        return list;
    }

    //Fonction pour modifier un equipement
    public Equipement updateEquipement(Equipement equipement){
        Equipement equipementVerif = equipementRepository.findEquipementByIdEquipement(equipement.getIdEquipement());
        if (equipementVerif != null) {
            return equipementRepository.save(equipement);
        }else
            throw new NotFoundException("invalid");
    }

    //Fonction pour supprimer un equipement
    public String deleteEquipemnt(int idEquipement){
        Equipement equipementVerif = equipementRepository.findEquipementByIdEquipement(idEquipement);
        if (equipementVerif != null) {
            equipementRepository.deleteById(idEquipement);
            return "succes";
        }else
            throw new NotFoundException("invalid");
    }
}
