package com.example.projet_de_fin_odk3.service;

import com.example.projet_de_fin_odk3.exception.AlreadyExistException;
import com.example.projet_de_fin_odk3.exception.NotFoundException;
import com.example.projet_de_fin_odk3.model.Utilisateur;
import com.example.projet_de_fin_odk3.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; //Encoder le mot de passe de l'utilisateur

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String sender;

    //Fonction pour verifier l'email du nouveau utilisateur s'il existe déjà
    public String verifyNewUserMail(String email) throws Exception {
        Utilisateur userVerif = utilisateurRepository.findByEmail(email);
        if (userVerif != null)
            throw new AlreadyExistException("exist");

        userVerif = new Utilisateur();
        userVerif.setEmail(email);
        String code = getRandomNumberString();
        sendMail(userVerif,code);
        return code;
    }

    //Fonction pour ajouter un utilisateur
    public Utilisateur addUser(Utilisateur utilisateur){
        Utilisateur userVerif = utilisateurRepository.findByEmail(utilisateur.getEmail());
        if (userVerif != null)
            throw new AlreadyExistException("exist");

        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

        return utilisateurRepository.save(utilisateur);
    }

    //Fonction pour connecter un utilisateur
    public Utilisateur connectUser(String email, String password){
        Utilisateur userVerif = utilisateurRepository.findByEmail(email);
        if (userVerif == null)
            throw new NotFoundException("email invalid");

        if (!passwordEncoder.matches(password, userVerif.getMotDePasse()))
            throw new NotFoundException("pass invalid");

        return userVerif;
    }

    //Fonction pour obtenir la liste des utilisateurs
    public List<Utilisateur> getAllUsers(){
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll(Sort.by(Sort.Direction.DESC,"idUtilisateur"));
        if (utilisateurList.isEmpty())
            throw new NotFoundException("empty");
        return utilisateurList;
    }

    //Fonction pour modifer un utilisateur
    public Utilisateur updateUser(Utilisateur utilisateur, String... oldPass){
        Utilisateur userVerif = utilisateurRepository.findByIdUtilisateur(utilisateur.getIdUtilisateur());
        if (userVerif == null)
            throw new NotFoundException("invalid");

        if (oldPass.length != 0){
            if(!passwordEncoder.matches(oldPass[0], userVerif.getMotDePasse()))
                throw new NotFoundException("old invalid");
            utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
            return utilisateurRepository.save(utilisateur);
        }
        return utilisateurRepository.save(utilisateur);
    }

    //Fonction pour verifier l'email de l'utilisateur
    public String verifyUserEmail(String email) throws Exception {
        Utilisateur userVerif = utilisateurRepository.findByEmail(email);
        if (userVerif == null)
            throw new NotFoundException("invalid");

        String code = getRandomNumberString();
        sendMail(userVerif,code);
        return code;
    }

    //Fonction pour reinitialiser le mot de passe
    public Utilisateur resetPassword(String email, String password){
        Utilisateur userVerif = utilisateurRepository.findByEmail(email);

        userVerif.setMotDePasse(passwordEncoder.encode(password));

        return utilisateurRepository.save(userVerif);
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

    private void sendMail(Utilisateur utilisateur, String code) throws Exception {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        try {
            mailMessage.setFrom(sender);
            mailMessage.setTo(utilisateur.getEmail());
            mailMessage.setText("Votre code de verification est "+code);
            mailMessage.setSubject("Validation email");

            javaMailSender.send(mailMessage);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
