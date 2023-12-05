package com.example.projet_de_fin_odk3.controller;

import com.example.projet_de_fin_odk3.configuration.ResponseHandler;
import com.example.projet_de_fin_odk3.model.Appareil;
import com.example.projet_de_fin_odk3.model.Equipement;
import com.example.projet_de_fin_odk3.service.EquipementService;
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
@RequestMapping("/equipement")
public class EquipementController {

    @Autowired
    private EquipementService equipementService;

    @PostMapping("/create")
    @Operation(summary = "Ajouter un equipement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Equipement ajouter avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Equipement.class))
            }),
            @ApiResponse(responseCode = "400",description = "equipement existe déjà", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur server", content = @Content)
    })
    public ResponseEntity<Object> ajouterEquipement(@Valid @RequestBody Equipement equipement){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,equipementService.addEquipement(equipement));
    }

    @GetMapping("/list")
    @Operation(summary = "Renvoi la liste des equipements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "La liste a été avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Equipement.class))
            }),
            @ApiResponse(responseCode = "400",description = "Aucun equipement trouvé", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur server", content = @Content)
    })
    public ResponseEntity<Object> afficherLaListe(){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,equipementService.getAllEquipement());
    }

    @PutMapping("/update")
    @Operation(summary = "Modifier un equipements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "L'equipement a été modifier avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Equipement.class))
            }),
            @ApiResponse(responseCode = "400",description = "Aucun equipement trouvé", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur server", content = @Content)
    })
    public ResponseEntity<Object> modifierEquipement(@Valid @RequestBody Equipement equipement){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,equipementService.updateEquipement(equipement));
    }

    @DeleteMapping("delete/{idEquipement}")
    @Operation(summary = "Supprimer un equipements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "L'equipement a été supprimer avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Equipement.class))
            }),
            @ApiResponse(responseCode = "400",description = "Aucun equipement trouvé", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur server", content = @Content)
    })
    public ResponseEntity<Object> supprimerEquipement(@PathVariable int idEquipement){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,equipementService.deleteEquipemnt(idEquipement));
    }
}
