package com.paevolution.appiogateway.core.customizer;

import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient.Builder;

/**
 * Fornisce una personalizzazione globale per tutte le istanze di WebClient cosicché possano includere nell'header lo
 * stesso User-Agent HTTP.
 * 
 * @author simone.vernata
 *
 */
@Component
public class UserAgentCustomizer implements WebClientCustomizer {

    @Override
    public void customize(Builder webClientBuilder) {

	webClientBuilder.defaultHeader(HttpHeaders.USER_AGENT, "appiogateway");
    }
}