package com.paevolution.appioproducer.exception;

import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.Data;

@Data
public class WebClientExceptionsWrapper extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -3275065166347512881L;
    private WebClientResponseException webClientResponseException;

    public WebClientExceptionsWrapper(WebClientResponseException webClientResponseException) {

	setWebClientResponseException(webClientResponseException);
    }
}
