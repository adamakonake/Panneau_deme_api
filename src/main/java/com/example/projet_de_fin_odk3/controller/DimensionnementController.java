package com.example.projet_de_fin_odk3.controller;

import com.example.projet_de_fin_odk3.configuration.ResponseHandler;
import com.example.projet_de_fin_odk3.model.Appareil;
import com.example.projet_de_fin_odk3.model.Dimensionnement;
import com.example.projet_de_fin_odk3.model.Electricien;
import com.example.projet_de_fin_odk3.service.DimensionnementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dimensionnement")
public class DimensionnementController {

    @Autowired
    DimensionnementService dimensionnementService;

    @PostMapping("/create/{idUser}")
    @Operation(summary = "Ajouter un dimensionnement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Dimensionnement ajouter avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Dimensionnement.class))
            }),
            @ApiResponse(responseCode = "400",description = "Dimensionnement existe déjà", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur server", content = @Content)
    })
    public ResponseEntity<Object> ajouterDimensionnement(@RequestParam("appareils") String appareils, @RequestParam("description") String description,
    @RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude,@PathVariable int idUser) throws Exception {
        List<Appareil> appareilList = new JsonMapper().readValue(appareils, new TypeReference<List<Appareil>>() {});

        Map<String,Double> coordonnee = new HashMap<>();
        coordonnee.put("latitude",latitude);
        coordonnee.put("longitude",longitude);
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,dimensionnementService.createDimensionnement(description,appareilList,coordonnee,idUser));
    }

    @GetMapping("/list/{idUser}")
    @Operation(summary = "Recupère la liste des dimensionnements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "La list a été récupérée avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Dimensionnement.class))
            }),
            @ApiResponse(responseCode = "400",description = "Aucun dimensionnement trouvé", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> getAllDimensionnementByUser(@PathVariable int idUser){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,dimensionnementService.allDImensionnementByUser(idUser));
    }

    @GetMapping("/{idDim}")
    @Operation(summary = "Recupère les informetions d'un dimensionnement donné")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Les informations ont été récupérée avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Dimensionnement.class))
            }),
            @ApiResponse(responseCode = "400",description = "Dimensionnement invalide", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur serveur", content = @Content),
    })
    public ResponseEntity<Object> getDimensionnementInfoById(@PathVariable int idDim){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,dimensionnementService.getInfoByDimensionnement(idDim));
    }



}
