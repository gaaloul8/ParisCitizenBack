package com.municipalite.paris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PlateformeParisApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlateformeParisApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  Plateforme Municipale Paris");
        System.out.println("  API démarrée sur http://localhost:8081");
        System.out.println("========================================\n");
    }
}


