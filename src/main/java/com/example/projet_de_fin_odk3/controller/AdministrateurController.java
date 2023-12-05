package com.example.projet_de_fin_odk3.controller;

import com.example.projet_de_fin_odk3.configuration.ResponseHandler;
import com.example.projet_de_fin_odk3.model.Administrateur;
import com.example.projet_de_fin_odk3.model.AuthPojo;
import com.example.projet_de_fin_odk3.model.Electricien;
import com.example.projet_de_fin_odk3.model.Utilisateur;
import com.example.projet_de_fin_odk3.service.AdministrateurService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdministrateurController {

    @Autowired
    AdministrateurService administrateurService;

    @PostMapping("/connect")
    @Operation(summary = "Connecter un administrateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Administrateur connecter avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Administrateur.class))
            }),
            @ApiResponse(responseCode = "400",description = "Identifiant incorrect", content = @Content),
    })
    public ResponseEntity<Object> connecterAdministrateur(@Valid @RequestBody AuthPojo auth){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,administrateurService.connectAdmin(auth.getEmail(), auth.getPassword()));
    }

    @PostMapping("/create")
    @Operation(summary = "Enregistrer un administrateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Administrateur a été enregistré avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Administrateur.class))
            }),
            @ApiResponse(responseCode = "400",description = "Email de l'administrteur existe déjà", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> ajouterAdministrateur(@RequestParam("administrateur") String administrateurString, @RequestParam(value ="photo", required=false) MultipartFile multipartFile) throws Exception {
        Administrateur administrateur;
        try{
            administrateur = new JsonMapper().readValue(administrateurString, Administrateur.class);
        }catch(JsonProcessingException e){
            throw new Exception(e.getMessage());
        }
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,administrateurService.addAdministrateur(administrateur,multipartFile));
    }

    @PutMapping("/update")
    @Operation(summary = "Modifier un administrateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Administrateur a été modifié avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Administrateur.class))
            }),
            @ApiResponse(responseCode = "400",description = "Administrateur invalid", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> modifierAdministrateur(@RequestParam("administrateur") String administrateurString, @RequestParam(value ="photo", required=false) MultipartFile multipartFile,
                                                         @RequestParam(value = "oldPassword", required=false) String oldPass) throws Exception {
        Administrateur administrateur;
        try{
            administrateur = new JsonMapper().readValue(administrateurString, Administrateur.class);
        }catch(JsonProcessingException e){
            throw new Exception(e.getMessage());
        }
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,administrateurService.updateAdministrateur(administrateur,multipartFile,oldPass));
    }

    @PutMapping("/change/{idAdmin}")
    @Operation(summary = "Désactiver ou activer un administrateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Administrateur a été désactiver ou activer avec succes", content = {
                    @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "400",description = "Administrateur invalid", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> desactiverOuActiverAdministrateur(@PathVariable int idAdmin) {
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,administrateurService.disableOrEnableAdmin(idAdmin));
    }

    @PutMapping("/changeState/{idAdmin}")
    @Operation(summary = "Changer l'accès d'un administrateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Accès changer avec succes", content = {
                    @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "400",description = "Administrateur invalid", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> changerAccesAdministrateur(@PathVariable int idAdmin) {
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,administrateurService.updateAdminState(idAdmin));
    }

    @GetMapping("/list")
    @Operation(summary = "Renvoie la liste des administrateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Liste retournée avec succes", content = {
                    @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "400",description = "Liste vide", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> listeAdministrateur() {
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,administrateurService.getAllAdmin());
    }

    @GetMapping("/statistics")
    @Operation(summary = "Renvoie la liste des statistiques")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Liste retournée avec succes", content = {
                    @Content(mediaType = "application/plain", schema = @Schema(implementation = List.class))
            }),
            @ApiResponse(responseCode = "400",description = "Liste vide", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> listeStatistiques() {
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,administrateurService.getStatistics());
    }

    @GetMapping("/verifmail")
    @Operation(summary = "Verifier l'email de l'administrateur en lui envoyant un code de verification dans son mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "L'email exist et le cade a été envoyer avec succès", content = {
                    @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> verifierEmail(@RequestParam("email") String email) throws Exception {
        return ResponseHandler.generateResponse(administrateurService.verifyAdminEmail(email), HttpStatus.OK,null);
    }

    @PutMapping("/resetpassword")
    @Operation(summary = "Réinitialise le mot de passe de l'administrateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Mot de passe réinitialisé avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Administrateur.class))
            }),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> resetPassword(@RequestParam("email") String email, @RequestParam("password") String password) throws Exception {
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,administrateurService.resetPassword(email,password));
    }
}
