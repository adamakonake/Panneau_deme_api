package com.example.projet_de_fin_odk3.service;

import com.example.projet_de_fin_odk3.exception.NotFoundException;
import com.example.projet_de_fin_odk3.model.*;
import com.example.projet_de_fin_odk3.repository.DimensionnementRepository;
import com.example.projet_de_fin_odk3.repository.ElectricienRepository;
import com.example.projet_de_fin_odk3.repository.NoteRepository;
import com.example.projet_de_fin_odk3.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private ElectricienRepository electricienRepository;
    @Autowired
    private DimensionnementRepository dimensionnementRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public NotePojo addAndUpdateNote(NotePojo notePojo){

        Note noteVerif = noteRepository.findByIdNote(notePojo.getIdNotePojo());
        if (noteVerif == null){
            Utilisateur utilisateur = utilisateurRepository.findByIdUtilisateur(notePojo.getIdUser());
            Electricien electricien = electricienRepository.findByIdElectricien(notePojo.getIdElec());
            Dimensionnement dimensionnement = dimensionnementRepository.findByIdDimensionnement(notePojo.getIdDim());
            if (utilisateur == null)
                throw new NotFoundException("user invalid");
            if (electricien == null)
                throw new NotFoundException("elec invalid");
            if (dimensionnement == null)
                throw new NotFoundException("dim invalid");
            Note note = new Note();
            note.setUtilisateur(utilisateur);
            note.setElectricien(electricien);
            note.setDimensionnement(dimensionnement);
            note.setValeur(note.getValeur());
            Note note1 = noteRepository.save(note);
            notePojo.setIdNotePojo(note1.getIdNote());
            return notePojo;
        }else {
            noteVerif.setValeur(notePojo.getValeur());
            noteRepository.save(noteVerif);
            return notePojo;
        }
    }
}
