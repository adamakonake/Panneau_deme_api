package com.example.projet_de_fin_odk3.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private int statusCode;
    private Date timestemp;
    private String message;
    private String description;
}
