package com.example.projet_de_fin_odk3.service;

import com.example.projet_de_fin_odk3.exception.AlreadyExistException;
import com.example.projet_de_fin_odk3.exception.NotFoundException;
import com.example.projet_de_fin_odk3.model.Appareil;
import com.example.projet_de_fin_odk3.repository.AppareilRepository;
import com.example.projet_de_fin_odk3.repository.ListAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppareilService {

    @Autowired
    AppareilRepository appareilRepository;

    @Autowired
    ListAppRepository listAppRepository;

    //Fonction pour ajouter un appareil
    public Appareil add(Appareil appareil){
        Appareil appareilVerif = appareilRepository.findByNomAndUtilisateurIdUtilisateur(appareil.getNom(),appareil.getUtilisateur().getIdUtilisateur());
        if (appareilVerif != null)
           throw new AlreadyExistException("exist");
        return appareilRepository.save(appareil);
    }

    public List<Appareil> appareilByUser(int idUser){
        List<Appareil> appareilList = appareilRepository.findByUtilisateurIdUtilisateur(idUser);
        if (appareilList == null || appareilList.isEmpty())
            throw new NotFoundException("empty");
        return appareilList;
    }

    public Appareil updateAppareil(Appareil appareil){
        Appareil appareilVerif = appareilRepository.findByIdAppareil(appareil.getIdAppareil());
        if (appareilVerif == null)
            throw new NotFoundException("invalid");
        return appareilRepository.save(appareil);
    }

    public String deleteAppareil(int idAppareil){
        Appareil appareil = appareilRepository.findByIdAppareil(idAppareil);
        if (appareil == null)
            throw new NotFoundException("invalid");
        if (!listAppRepository.findByAppareilIdAppareil(idAppareil).isEmpty())
            throw new AlreadyExistException("impossible");
        appareilRepository.delete(appareil);
        return "succes";
    }
}
