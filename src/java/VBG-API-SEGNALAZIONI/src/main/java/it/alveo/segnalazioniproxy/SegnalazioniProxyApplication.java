package it.alveo.segnalazioniproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SegnalazioniProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SegnalazioniProxyApplication.class, args);
    }

}
