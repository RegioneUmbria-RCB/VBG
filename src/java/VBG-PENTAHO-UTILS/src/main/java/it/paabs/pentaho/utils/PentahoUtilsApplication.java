package it.paabs.pentaho.utils;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

import com.fasterxml.jackson.databind.Module;

@SpringBootApplication(nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class)
@ComponentScan(basePackages = { "it.paabs.pentaho.utils" }, nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class)
public class PentahoUtilsApplication {

    public static void main(String[] args) {

	SpringApplication.run(PentahoUtilsApplication.class, args);
    }

    @Bean(name = "it.paabs.pentaho.utils.PentahoUtilsApplication.jsonNullableModule")
    public Module jsonNullableModule() {

	return new JsonNullableModule();
    }
}
