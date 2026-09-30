package com.paevolution.appiogateway.openapi;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class AppIOGatewayOpenAPIConfig {

    @Bean
    public GroupedOpenApi publicApi() {

	return GroupedOpenApi.builder().group("appiogateway-public").pathsToMatch("/messages/**").build();
    }

    @Bean
    public OpenAPI appiogatewayOpenAPI() {

	return new OpenAPI().info(new Info().title("AppIOGateway API").description("Connettore ad IO Italia").version("v0.0.1")
		.license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
