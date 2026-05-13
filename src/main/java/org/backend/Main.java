package org.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class Main {
  public static void main(String[] args) {
    // Load .env file if it exists
    Dotenv dotenv = Dotenv.configure()
        .ignoreIfMissing()
        .load();

    // Set system properties from .env
    dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

    SpringApplication.run(Main.class, args);
  }
}