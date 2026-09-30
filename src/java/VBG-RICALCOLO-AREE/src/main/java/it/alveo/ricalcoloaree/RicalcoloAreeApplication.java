package it.alveo.ricalcoloaree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RicalcoloAreeApplication {

    public static void main(String[] args) {

	SpringApplication.run(RicalcoloAreeApplication.class, args);
    }
}
