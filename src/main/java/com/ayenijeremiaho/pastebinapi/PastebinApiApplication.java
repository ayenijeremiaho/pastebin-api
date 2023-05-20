package com.ayenijeremiaho.pastebinapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PastebinApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PastebinApiApplication.class, args);
    }

    CommandLineRunner commandLineRunner = args -> {

    };
}
