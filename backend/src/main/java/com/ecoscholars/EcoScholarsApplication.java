package com.ecoscholars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main Spring Boot Application class for Eco-Scholars
 * Student Plant Growth Tracking System
 */
@SpringBootApplication
@EnableJpaAuditing
public class EcoScholarsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcoScholarsApplication.class, args);
    }
}
