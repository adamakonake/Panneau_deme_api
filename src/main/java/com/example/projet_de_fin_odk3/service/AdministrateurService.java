package com.example.projet_de_fin_odk3.service;

import com.example.projet_de_fin_odk3.exception.AlreadyExistException;
import com.example.projet_de_fin_odk3.exception.NotFoundException;
import com.example.projet_de_fin_odk3.model.Administrateur;
import com.example.projet_de_fin_odk3.model.Electricien;
import com.example.projet_de_fin_odk3.model.Utilisateur;
import com.example.projet_de_fin_odk3.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AdministrateurService {

    @Autowired
    private AdministrateurRepository administrateurRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private EquipementRepository equipementRepository;

    @Autowired
    private TypeEquipementRepository typeEquipementRepository;

    @Autowired
    private ElectricienRepository electricienRepository;

    @Autowired
    private DimensionnementRepository dimensionnementRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String sender;

    @PostConstruct
    private void init(){
        if(administrateurRepository.findAdministrateurByEmail("adamakonake@gmail.com") == null){
            Administrateur administrateur = new Administrateur();
            administrateur.setNom("Konake");
            administrateur.setPrenom("Adama");
            administrateur.setEmail("adamakonake@gmail.com");
            administrateur.setTelephone("77-77-77-77");
            administrateur.setMotDePasse("$2a$10$UlpXsTzyfiqa4Wc7UT7AOOELw0haHtM9O3RBNpzparGHAtn.R.Rai");
            administrateur.setActive(true);
            administrateur.setSuperAdmin(true);
            administrateur.setPhoto("/panneau/administrateurs/adamakonake@gmail.com.png");
            administrateurRepository.save(administrateur);
        }
    }

    //Fonction pour connecter un admin
    public Administrateur connectAdmin(String email, String password){
        Administrateur administrateur = administrateurRepository.findAdministrateurByEmail(email);
        if (administrateur == null)
            throw new NotFoundException("email invalid");

        if (!passwordEncoder.matches(password, administrateur.getMotDePasse()))
            throw new NotFoundException("pass invalid");

        if (!administrateur.isActive())
            throw new NotFoundException("disabled");

        return administrateur;
    }

    //Fonction pour obtenir la liste des administrateur
    public List<Administrateur> getAllAdmin(){
        List<Administrateur> administrateurList = administrateurRepository.findAll(Sort.by(Sort.Direction.DESC,"idAdministrateur"));
        if (administrateurList.isEmpty())
            throw new NotFoundException("empty");
        return administrateurList;
    }

    //Fonction pour ajouter un administrateur
    public Administrateur addAdministrateur(Administrateur administrateur, MultipartFile multipartFile) throws Exception {

        Administrateur administrateurVerif = administrateurRepository.findAdministrateurByEmail(administrateur.getEmail());
        if(administrateurVerif != null)
            throw new AlreadyExistException("exist");

        if (multipartFile != null){

            String location = "C:\\xampp\\htdocs\\panneau\\administrateurs";
            int index = multipartFile.getOriginalFilename().lastIndexOf(".");
            String imgName = administrateur.getEmail()+multipartFile.getOriginalFilename().substring(index);
            try{
                Path rootlocation = Paths.get(location);
                if(!Files.exists(rootlocation)){
                    Files.createDirectories(rootlocation);
                    Files.copy(multipartFile.getInputStream(),rootlocation.resolve(imgName));
                    administrateur.setPhoto("/panneau/administrateurs/"+imgName);
                }else{
                    try{
                        String nom = location+"\\"+imgName;
                        Path name = Paths.get(nom);
                        if(!Files.exists(name)){
                            Files.copy(multipartFile.getInputStream(),rootlocation.resolve(imgName));
                            administrateur.setPhoto("/panneau/administrateurs/"+imgName);
                        }else{
                            Files.delete(name);
                            Files.copy(multipartFile.getInputStream(),rootlocation.resolve(imgName));
                            administrateur.setPhoto("/panneau/administrateurs/"+imgName);
                        }
                    }catch(Exception e){
                        throw new Exception("some error");
                    }
                }
            }catch(Exception e){
                throw new Exception(e.getMessage());
            }

        }

        administrateur.setMotDePasse(passwordEncoder.encode("Panneau@2023"));

        return administrateurRepository.save(administrateur);

    }

    //Fonction pour modifier un administrateur
    public Administrateur updateAdministrateur(Administrateur administrateur, MultipartFile multipartFile, String oldPass) throws Exception {

        Administrateur administrateurVerif = administrateurRepository.findAdministrateurByEmail(administrateur.getEmail());
        if(administrateurVerif == null)
            throw new NotFoundException("invalid");

        if (oldPass != null){
            Administrateur administrateurVerifEmail = administrateurRepository.findAdministrateurByEmail(administrateur.getEmail());

            String pass = administrateurVerif.getMotDePasse();
            if (!passwordEncoder.matches(oldPass,pass))
                throw new NotFoundException("wrong");

            administrateur.setMotDePasse(passwordEncoder.encode(administrateur.getMotDePasse()));
        }

        if (multipartFile != null){

            String location = "C:\\xampp\\htdocs\\panneau\\administrateurs";
            int index = multipartFile.getOriginalFilename().lastIndexOf(".");
            String imgName = administrateur.getEmail()+multipartFile.getOriginalFilename().substring(index);
            try{
                Path rootlocation = Paths.get(location);
                if(!Files.exists(rootlocation)){
                    Files.createDirectories(rootlocation);
                    Files.copy(multipartFile.getInputStream(),rootlocation.resolve(imgName));
                    administrateur.setPhoto("/panneau/administrateurs/"+imgName);
                }else{
                    try{
                        String nom = location+"\\"+imgName;
                        Path name = Paths.get(nom);
                        if(!Files.exists(name)){
                            Files.copy(multipartFile.getInputStream(),rootlocation.resolve(imgName));
                            administrateur.setPhoto("/panneau/administrateurs/"+imgName);
                        }else{
                            Files.delete(name);
                            Files.copy(multipartFile.getInputStream(),rootlocation.resolve(imgName));
                            administrateur.setPhoto("/panneau/administrateurs/"+imgName);
                        }
                    }catch(Exception e){
                        throw new Exception("some error");
                    }
                }
            }catch(Exception e){
                throw new Exception(e.getMessage());
            }

        }

        return administrateurRepository.save(administrateur);

    }

    //Fonction pour activer ou desactiver un administrteur
    public String disableOrEnableAdmin(int idAdmin){
        Administrateur administrateurVerif = administrateurRepository.findAdministrateurByIdAdministrateur(idAdmin);
        if(administrateurVerif == null)
            throw new NotFoundException("invalid");

        administrateurVerif.setActive(!administrateurVerif.isActive());
        administrateurRepository.save(administrateurVerif);
        return "succes";
    }

    //Fonction pour Changer l'access de l'administrateur
    public String updateAdminState(int idAdmin){
        Administrateur administrateurVerif = administrateurRepository.findAdministrateurByIdAdministrateur(idAdmin);
        if(administrateurVerif == null)
            throw new NotFoundException("invalid");

        administrateurVerif.setSuperAdmin(!administrateurVerif.isSuperAdmin());
        administrateurRepository.save(administrateurVerif);
        return "succes";
    }

    //Fonction pour verifier l'email de l'utilisateur
    public String verifyAdminEmail(String email) throws Exception {
        Administrateur administrateurVerif = administrateurRepository.findAdministrateurByEmail(email);
        if (administrateurVerif == null)
            throw new NotFoundException("invalid");

        String code = getRandomNumberString();
        sendMail(administrateurVerif,code);
        return code;
    }

    //Fonction pour reinitialiser le mot de passe
    public Administrateur resetPassword(String email, String password){
        Administrateur administrateurVerif = administrateurRepository.findAdministrateurByEmail(email);

        administrateurVerif.setMotDePasse(passwordEncoder.encode(password));

        return administrateurRepository.save(administrateurVerif);
    }

    //Fonction pour générer 6 chiffres en chaîne de caractère
    private String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    //Fonction pour envoyer email
    private void sendMail(Administrateur administrateur, String code) throws Exception {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        try {
            mailMessage.setFrom(sender);
            mailMessage.setTo(administrateur.getEmail());
            mailMessage.setText("Votre code de verification est "+code);
            mailMessage.setSubject("Validation email");

            javaMailSender.send(mailMessage);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Long> getStatistics(){
        List<Long> list = new ArrayList<>();
        list.add(equipementRepository.count());
        list.add(typeEquipementRepository.count());
        list.add(electricienRepository.count());
        list.add(administrateurRepository.count());
        list.add(utilisateurRepository.count());
        list.add(dimensionnementRepository.count());
        return list;
    }
}
