package it.alveo.firmaremota.aruba;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

import com.fasterxml.jackson.databind.Module;

@SpringBootApplication(nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class)
@ComponentScan(basePackages = { "it.alveo.firmaremota.aruba" }, nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class)
public class FirmaRemotaArubaApplication {

    public static void main(String[] args) {

	SpringApplication.run(FirmaRemotaArubaApplication.class, args);
    }

    @Bean(name = "it.alveo.firmaremota.aruba.FirmaRemotaArubaApplication.jsonNullableModule")
    public Module jsonNullableModule() {

	return new JsonNullableModule();
    }
}
