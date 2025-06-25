package com.example.lionking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LionKingApplication {

    public static void main(String[] args) {
        SpringApplication.run(LionKingApplication.class, args);
    }
}