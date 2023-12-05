package com.example.projet_de_fin_odk3.controller;

import com.example.projet_de_fin_odk3.configuration.ResponseHandler;
import com.example.projet_de_fin_odk3.model.Electricien;
import com.example.projet_de_fin_odk3.model.Equipement;
import com.example.projet_de_fin_odk3.service.ElectricienService;
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

@RestController
@RequestMapping("/electricien")
public class ElectricienController {

    @Autowired
    private ElectricienService electricienService;

    @PostMapping("/create")
    @Operation(summary = "Ajouter un electricien")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Electricien ajouter avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Electricien.class))
            }),
            @ApiResponse(responseCode = "400",description = "Electricien existe déjà", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content)
    })
    public ResponseEntity<Object> ajouterElectricien(@RequestParam("electricien") String electricienString, @RequestParam(value ="photo", required=false) MultipartFile multipartFile) throws Exception {

        Electricien electricien;
        try{
            electricien = new JsonMapper().readValue(electricienString, Electricien.class);
        }catch(JsonProcessingException e){
            throw new Exception(e.getMessage());
        }
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,electricienService.addElectricien(electricien,multipartFile));
    }

    @PutMapping("/update")
    @Operation(summary = "Modifier un electricien")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Electricien modifier avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Electricien.class))
            }),
            @ApiResponse(responseCode = "400",description = "Electricien invalide", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content)
    })
    public ResponseEntity<Object> modofierElectricien(@RequestParam("electricien") String electricienString, @RequestParam(value ="photo", required=false) MultipartFile multipartFile) throws Exception {

        Electricien electricien;
        try{
            electricien = new JsonMapper().readValue(electricienString, Electricien.class);
        }catch(JsonProcessingException e){
            throw new Exception(e.getMessage());
        }
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,electricienService.updateElectricien(electricien,multipartFile));
    }

    @GetMapping("/listActive")
    @Operation(summary = "Obtenir la liste des electriciens active")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Liste retournée avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Electricien.class))
            }),
            @ApiResponse(responseCode = "400",description = "Aucun electricien trouveé", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content)
    })
    public ResponseEntity<Object> listActiveElectriciens() {
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,electricienService.getActiveElectricien());
    }

    @GetMapping("/listAll")
    @Operation(summary = "Obtenir la liste des electriciens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Liste retournée avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Electricien.class))
            }),
            @ApiResponse(responseCode = "400",description = "Aucun electricien trouveé", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content)
    })
    public ResponseEntity<Object> allElectricien() {
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,electricienService.getAllElectricien());
    }

    @PutMapping("/change/{idElectricien}")
    @Operation(summary = "Changer l'état d'un electricien")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "L'état à été changer avec succès", content = {
                    @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "400",description = "Aucun electricien trouveé", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content)
    })
    public ResponseEntity<Object> updateElectricienStatus(@PathVariable int idElectricien) {
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,electricienService.disableOrEnableEletricien(idElectricien));
    }
}
