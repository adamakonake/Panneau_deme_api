package com.example.projet_de_fin_odk3.service;

import com.example.projet_de_fin_odk3.exception.AlreadyExistException;
import com.example.projet_de_fin_odk3.exception.NotFoundException;
import com.example.projet_de_fin_odk3.model.Electricien;
import com.example.projet_de_fin_odk3.model.Note;
import com.example.projet_de_fin_odk3.repository.ElectricienRepository;
import com.example.projet_de_fin_odk3.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ElectricienService {

    @Autowired
    private ElectricienRepository electricienRepository;

    @Autowired
    NoteRepository noteRepository;

    //Fonction pour ajouter un electricien
    public Electricien addElectricien(Electricien electricien, MultipartFile multipartFile) throws Exception {
        Electricien electricienVerif = electricienRepository.findByEmail(electricien.getEmail());
        if(electricienVerif != null)
            throw new AlreadyExistException("exist");

        if (multipartFile != null){

            String location = "C:\\xampp\\htdocs\\panneau\\electriciens";
            int index = multipartFile.getOriginalFilename().lastIndexOf(".");
            String imgName = electricien.getEmail()+multipartFile.getOriginalFilename().substring(index);
            try{
                Path rootlocation = Paths.get(location);
                if(!Files.exists(rootlocation)){
                    Files.createDirectories(rootlocation);
                    Files.copy(multipartFile.getInputStream(),rootlocation.resolve(imgName));
                    electricien.setPhoto("/panneau/electriciens/"+imgName);
                }else{
                    try{
                        String nom = location+"\\"+imgName;
                        Path name = Paths.get(nom);
                        if(!Files.exists(name)){
                            Files.copy(multipartFile.getInputStream(),rootlocation.resolve(imgName));
                            electricien.setPhoto("/panneau/electriciens/"+imgName);
                        }else{
                            Files.delete(name);
                            Files.copy(multipartFile.getInputStream(),rootlocation.resolve(imgName));
                            electricien.setPhoto("/panneau/electriciens/"+imgName);
                        }
                    }catch(Exception e){
                        throw new Exception("some error");
                    }
                }
            }catch(Exception e){
                throw new Exception(e.getMessage());
            }

        }

        return electricienRepository.save(electricien);
    }

    //Fonction pour modifier un electricien
    public Electricien updateElectricien(Electricien electricien, MultipartFile multipartFile) throws Exception {
        Electricien electricienVerif = electricienRepository.findByIdElectricien(electricien.getIdElectricien());
        if (electricienVerif == null)
            throw new NotFoundException("invalid");

        if (multipartFile != null){

            String location = "C:\\xampp\\htdocs\\panneau\\electriciens";
            int index = multipartFile.getOriginalFilename().lastIndexOf(".");
            String imgName = electricien.getEmail()+multipartFile.getOriginalFilename().substring(index);
            try{
                Path rootlocation = Paths.get(location);
                if(!Files.exists(rootlocation)){
                    Files.createDirectories(rootlocation);
                    Files.copy(multipartFile.getInputStream(),rootlocation.resolve(imgName));
                    electricien.setPhoto("/panneau/electriciens/"+imgName);
                }else{
                    try{
                        String nom = location+"\\"+imgName;
                        Path name = Paths.get(nom);
                        if(!Files.exists(name)){
                            Files.copy(multipartFile.getInputStream(),rootlocation.resolve(imgName));
                            electricien.setPhoto("/panneau/electriciens/"+imgName);
                        }else{
                            Files.delete(name);
                            Files.copy(multipartFile.getInputStream(),rootlocation.resolve(imgName));
                            electricien.setPhoto("/panneau/electriciens/"+imgName);
                        }
                    }catch(Exception e){
                        throw new Exception("some error");
                    }
                }
            }catch(Exception e){
                throw new Exception(e.getMessage());
            }

        }

        System.out.println(electricien.toString());

        return electricienRepository.save(electricien);
    }

    //Fonction pour recupérer la liste de electriciens
    public Map<String, Object> getAllElectricien(){
        List<Electricien> list = electricienRepository.findAll(Sort.by(Sort.Direction.DESC,"idElectricien"));
        List<Double> listNoteValue = new ArrayList<>();
        List<Integer> listNmbreNote = new ArrayList<>();
        if (list.isEmpty())
            throw new NotFoundException("empty");

        list.forEach((electricien -> {
            List<Note> noteList = noteRepository.findByElectricienIdElectricien(electricien.getIdElectricien());
            if (noteList.isEmpty()){
                listNoteValue.add(0.0);
                listNmbreNote.add(0);
            }else {
                double somme = 0;
                for (Note note : noteList){
                    somme = somme + note.getValeur();
                }
                double totailNote = noteList.size()*5;
                double note = somme*5/totailNote;
                listNoteValue.add(note);
                listNmbreNote.add(noteList.size());
            }
        }));
        Map<String,Object> map = new HashMap<>();
        map.put("electriciens",list);
        map.put("notes",listNoteValue);
        map.put("nmbreNote",listNmbreNote);
        return map;
    }

    public Map<String, Object> getActiveElectricien(){
        List<Electricien> list = electricienRepository.findElectricienByActiveOrderByIdElectricienDesc(true);
        List<Double> listNoteValue = new ArrayList<>();
        List<Integer> listNmbreNote = new ArrayList<>();
        if (list.isEmpty())
            throw new NotFoundException("empty");

        list.forEach((electricien -> {
            List<Note> noteList = noteRepository.findByElectricienIdElectricien(electricien.getIdElectricien());
            if (noteList.isEmpty()){
                listNoteValue.add(0.0);
                listNmbreNote.add(0);
            }else {
                double somme = 0;
                for (Note note : noteList){
                    somme = somme + note.getValeur();
                }
                double totailNote = noteList.size()*5;
                double note = somme*5/totailNote;
                listNoteValue.add(note);
                listNmbreNote.add(noteList.size());
            }
        }));
        Map<String,Object> map = new HashMap<>();
        map.put("electriciens",list);
        map.put("notes",listNoteValue);
        map.put("nmbreNote",listNmbreNote);
        return map;
    }

    //Fonction pour désactiver un electricien
    public String disableOrEnableEletricien(int idElectricien){
        Electricien electricienVerif = electricienRepository.findByIdElectricien(idElectricien);
        if (electricienVerif == null)
            throw new NotFoundException("invalid");

        electricienVerif.setActive(!electricienVerif.isActive());
        electricienRepository.save(electricienVerif);
        return "succes";
    }
}
