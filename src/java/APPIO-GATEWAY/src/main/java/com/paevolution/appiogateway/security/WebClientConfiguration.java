package com.paevolution.appiogateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.paevolution.appiogateway.configuration.AppIOProperties;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebClientConfiguration {

    @Autowired
    private AppIOProperties properties;

    @Bean
    public WebClient ioAPIBackendWebClient(WebClient.Builder webClientBuilder) {

	return webClientBuilder.baseUrl(properties.getIOAPIEndpointURL()).build();
    }
}
