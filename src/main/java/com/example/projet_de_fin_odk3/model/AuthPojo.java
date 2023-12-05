package com.example.projet_de_fin_odk3.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthPojo {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
