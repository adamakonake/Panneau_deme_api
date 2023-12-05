package com.example.projet_de_fin_odk3.controller;

import com.example.projet_de_fin_odk3.configuration.ResponseHandler;
import com.example.projet_de_fin_odk3.model.Appareil;
import com.example.projet_de_fin_odk3.service.AppareilService;
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

import java.awt.*;

@RestController
@RequestMapping("appareil")
public class AppareilController {

    @Autowired
    private AppareilService appareilService;

    @PostMapping("/create")
    @Operation(summary = "Ajouter un appareil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Appareil ajouter avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Appareil.class))
            }),
            @ApiResponse(responseCode = "400",description = "Appareil existe déjà", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur server", content = @Content)
    })
    public ResponseEntity<Object> ajouterAppareil(@Valid @RequestBody Appareil appareil){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,appareilService.add(appareil));
    }

    @GetMapping("/{idUSer}/appareils")
    @Operation(summary = "Recupère tous les appareils d'un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "La list a été récupérée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Appareil.class))
            }),
            @ApiResponse(responseCode = "400",description = "Aucun appareil trouvé", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> getAppareilByUser(@PathVariable int idUSer){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,appareilService.appareilByUser(idUSer));
    }

    @PutMapping("/update")
    @Operation(summary = "Met à jour un appareil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "L'appareil a été mis à jour avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Appareil.class))
            }),
            @ApiResponse(responseCode = "400",description = "Appareil invalid", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> updateUserAppareil(@Valid @RequestBody Appareil appareil){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,appareilService.updateAppareil(appareil));
    }

    @DeleteMapping("/delete/{idAppareil}")
    @Operation(summary = "Supprime un appareil par son id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "L'appareil a été supprimée avec succès", content = {
                    @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "400",description = "Appareil invalid", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> deleteUserAppareil(@PathVariable int idAppareil){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,appareilService.deleteAppareil(idAppareil));
    }


}
