package com.example.projet_de_fin_odk3.service;

import com.example.projet_de_fin_odk3.exception.AlreadyExistException;
import com.example.projet_de_fin_odk3.exception.NotFoundException;
import com.example.projet_de_fin_odk3.model.TypeEquipement;
import com.example.projet_de_fin_odk3.repository.TypeEquipementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeEquipementService {

    @Autowired
    private TypeEquipementRepository typeEquipementRepository;

    // Fonction pour ajouter un type equipement
    public TypeEquipement addTypeEquipement(TypeEquipement typeEquipement){
        TypeEquipement typeEquipementVerif = typeEquipementRepository.findTypeEquipementByTitre(typeEquipement.getTitre());
        if (typeEquipementVerif != null)
            throw new AlreadyExistException("exist");
        return typeEquipementRepository.save(typeEquipement);
    }

    // Fonction pour avoir la liste des types d'équipement
    public List<TypeEquipement> getAllTypeEquipement(){
        List<TypeEquipement> list = typeEquipementRepository.findAll(Sort.by(Sort.Direction.DESC, "idTypeEquipement"));
        if (list.isEmpty())
            throw new NotFoundException("empty");
        return list;
    }

    //Fonction pour modifier un type equipement
    public TypeEquipement editTypeEquipement(TypeEquipement type){
        TypeEquipement typeEquipement = typeEquipementRepository.findTypeEquipementByIdTypeEquipement(type.getIdTypeEquipement());
        if (typeEquipement != null){
            TypeEquipement typeEquipementVerif = typeEquipementRepository.findTypeEquipementByTitre(type.getTitre());
            if (typeEquipementVerif != null)
                throw new AlreadyExistException("exist");
            return typeEquipementRepository.save(type);
        }else
            throw new NotFoundException("invalid");
    }

    //Fonction pour supprimer un type equipement
    public String deleteTypeEquipement(int idType){
        TypeEquipement typeEquipement = typeEquipementRepository.findTypeEquipementByIdTypeEquipement(idType);
        if (typeEquipement != null){
            typeEquipementRepository.deleteById(idType);
            return "succes";
        }else
            throw new NotFoundException("invalid");
    }
}
