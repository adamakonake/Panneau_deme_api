package com.example.projet_de_fin_odk3.controller;

import com.example.projet_de_fin_odk3.configuration.ResponseHandler;
import com.example.projet_de_fin_odk3.model.AuthPojo;
import com.example.projet_de_fin_odk3.model.Utilisateur;
import com.example.projet_de_fin_odk3.service.UtilisateurService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/create")
    @Operation(summary = "Enregistrer un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Utilisateur enregistrer avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Utilisateur.class))
            }),
            @ApiResponse(responseCode = "400",description = "Utilisateur existe déjà", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur server", content = @Content)
    })
    public ResponseEntity<Object> ajouterUtilisateur(@Valid @RequestBody Utilisateur utilisateur){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,utilisateurService.addUser(utilisateur));
    }

    @PostMapping("/connect")
    @Operation(summary = "Connecter un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Utilisateur connecter avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Utilisateur.class))
            }),
            @ApiResponse(responseCode = "400",description = "Identifiant incorrect", content = @Content),
    })
    public ResponseEntity<Object> connecterUtilisateur(@Valid @RequestBody AuthPojo auth){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,utilisateurService.connectUser(auth.getEmail(), auth.getPassword()));
    }

    @PutMapping("/update")
    @Operation(summary = "Modifier un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Utilisateur modifier avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Utilisateur.class))
            }),
            @ApiResponse(responseCode = "400",description = "Cet Utilisateur n'existe pas", content = @Content),
    })
    public ResponseEntity<Object> updateUtilisateur(@RequestParam("user") String userString, @RequestParam(value = "oldPass", required = false) String oldPass) throws Exception {
        Utilisateur utilisateur ;
        try {
            utilisateur =new JsonMapper().readValue(userString,Utilisateur.class);
        }catch (JsonProcessingException e){
            throw new Exception(e.getMessage());
        }
        if (oldPass == null)
            return ResponseHandler.generateResponse("succes", HttpStatus.OK,utilisateurService.updateUser(utilisateur));
        else
            return ResponseHandler.generateResponse("succes", HttpStatus.OK,utilisateurService.updateUser(utilisateur,oldPass));
    }

    @GetMapping("/list")
    @Operation(summary = "Retourne la liste des utilisateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "La liste a été retournée avec succès", content = {
                    @Content(mediaType = "applocation/json", schema = @Schema(implementation = Utilisateur.class))
            }),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> listUtilisateurs() {
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,utilisateurService.getAllUsers());
    }

    @GetMapping("/verifmail")
    @Operation(summary = "Verifier l'email de l'utilisateur en lui envoyant un code de verification dans son mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "L'email exist et le cade a été envoyer avec succès", content = {
                    @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> verifierEmail(@RequestParam("email") String email) throws Exception {
        return ResponseHandler.generateResponse(utilisateurService.verifyUserEmail(email), HttpStatus.OK,null);
    }

    @GetMapping("/verifnewmail")
    @Operation(summary = "Verifier l'email du nouveau l'utilisateur en lui envoyant un code de verification dans son mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "L'email exist et le cade a été envoyer avec succès", content = {
                    @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> verifierNewUserEmail(@RequestParam("email") String email) throws Exception {
        return ResponseHandler.generateResponse(utilisateurService.verifyNewUserMail(email), HttpStatus.OK,null);
    }

    @PutMapping("/resetpassword")
    @Operation(summary = "Réinitialise le mot de passe de l'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Mot de passe réinitialisé avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Utilisateur.class))
            }),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> resetPassword(@RequestParam("email") String email, @RequestParam("password") String password) throws Exception {
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,utilisateurService.resetPassword(email,password));
    }

}
