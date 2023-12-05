package com.example.projet_de_fin_odk3.repository;

import com.example.projet_de_fin_odk3.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note,Integer> {
    List<Note> findByElectricienIdElectricien(int idElectricien);
    Note findByUtilisateurIdUtilisateurAndElectricienIdElectricienAndDimensionnementIdDimensionnement(int idUser, int idElec, int idDim);
    Note findByIdNote(int idNote);
    Note findByDimensionnementIdDimensionnement(int idDim);
}
