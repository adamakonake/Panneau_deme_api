package com.example.projet_de_fin_odk3.controller;

import com.example.projet_de_fin_odk3.configuration.ResponseHandler;
import com.example.projet_de_fin_odk3.model.Appareil;
import com.example.projet_de_fin_odk3.model.TypeEquipement;
import com.example.projet_de_fin_odk3.service.TypeEquipementService;
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

@RestController
@RequestMapping("/type")
public class TypeEquipementController {

    @Autowired
    private TypeEquipementService typeEquipementService;

    @PostMapping("/create")
    @Operation(summary = "Ajouter un type equipement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Type equipement ajouter avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TypeEquipement.class))
            }),
            @ApiResponse(responseCode = "400",description = "Type equipement existe déjà", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur server", content = @Content)
    })
    public ResponseEntity<Object> ajouterAppareil(@Valid @RequestBody TypeEquipement typeEquipement){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,typeEquipementService.addTypeEquipement(typeEquipement));
    }

    @GetMapping("list")
    @Operation(summary = "Retourne la liste des types equipements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "la liste a été retourner avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TypeEquipement.class))
            }),
            @ApiResponse(responseCode = "400",description = "Aucun type trouvé", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur server", content = @Content)
    })
    public ResponseEntity<Object> allTypeEquipement(){
        return ResponseHandler.generateResponse("succes",HttpStatus.OK,typeEquipementService.getAllTypeEquipement());
    }

    @PutMapping("/edit")
    @Operation(summary = "Modifier un types equipements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "le type a été modifié avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TypeEquipement.class))
            }),
            @ApiResponse(responseCode = "400",description = "Aucun type trouvé", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur server", content = @Content)
    })
    public ResponseEntity<Object> editTypeEquipement(@Valid @RequestBody TypeEquipement typeEquipement){
        return ResponseHandler.generateResponse("succes",HttpStatus.OK,typeEquipementService.editTypeEquipement(typeEquipement));
    }

    @DeleteMapping("/delete/{idType}")
    @Operation(summary = "Supprimer un types equipements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "le type a été supprimé avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TypeEquipement.class))
            }),
            @ApiResponse(responseCode = "400",description = "Aucun type trouvé", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur server", content = @Content)
    })
    public ResponseEntity<Object> editTypeEquipement(@PathVariable int idType ){
        return ResponseHandler.generateResponse("succes",HttpStatus.OK,typeEquipementService.deleteTypeEquipement(idType));
    }

}
