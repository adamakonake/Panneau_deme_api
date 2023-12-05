package com.example.projet_de_fin_odk3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PanneauDemeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PanneauDemeApplication.class, args);
	}

}
