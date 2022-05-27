package com.paevolution.appioproducer.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebClientConfiguration {

    private AppIOProperties properties;

    @Autowired
    public void setProperties(AppIOProperties properties) {

	this.properties = properties;
    }

    @Bean
    public WebClient appiogatewayWSClient(WebClient.Builder webClientBuilder) {

	List<MediaType> acceptHeaders = new ArrayList<>();
	acceptHeaders.add(MediaType.APPLICATION_JSON);
	return webClientBuilder.baseUrl(properties.appiogatewayURL())
		.filter(ExchangeFilterFunctions.basicAuthentication(properties.vbgUsername(), properties.vbgPassword()))
		.defaultHeaders(httpHeaders -> {
		    httpHeaders.setAccept(acceptHeaders);
		    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		}).build();
    }
}