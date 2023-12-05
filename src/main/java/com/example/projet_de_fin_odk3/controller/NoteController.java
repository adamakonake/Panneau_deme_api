package com.example.projet_de_fin_odk3.controller;

import com.example.projet_de_fin_odk3.configuration.ResponseHandler;
import com.example.projet_de_fin_odk3.model.Appareil;
import com.example.projet_de_fin_odk3.model.Note;
import com.example.projet_de_fin_odk3.model.NotePojo;
import com.example.projet_de_fin_odk3.service.NoteService;
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
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/create")
    @Operation(summary = "Noter un electricien")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Electricien noter avec succes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotePojo.class))
            }),
            @ApiResponse(responseCode = "400",description = "Appareil existe déjà", content = @Content),
            @ApiResponse(responseCode = "500",description = "Erreur server", content = @Content)
    })
    public ResponseEntity<Object> noterElectricien(@Valid @RequestBody NotePojo notePojo){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK,noteService.addAndUpdateNote(notePojo));
    }
}
