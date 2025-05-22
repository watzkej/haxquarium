package com.haxquarium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for HaxQuarium - a security training application
 * themed as an online aquarium store that demonstrates OWASP Top 10 vulnerabilities.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
