package com.example.projet_de_fin_odk3.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI panneauDemeOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Panneau Dèmè")
                        .description("API pour le dimensionnement solaire")
                        .version("1.0"));
    }

}
