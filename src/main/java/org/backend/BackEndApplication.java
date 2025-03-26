package org.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }

}
//u be lidhja me databaze dhe poashtu konfigurimet per JPA
// Konfigurimet e bera gjenden ne resources/application.properties