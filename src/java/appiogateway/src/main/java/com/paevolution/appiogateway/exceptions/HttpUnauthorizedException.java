package com.paevolution.appiogateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class HttpUnauthorizedException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6195502835919800319L;
    private WebClientResponseException webClientResponseException;

    public HttpUnauthorizedException(String message) {

	super(message);
    }

    public HttpUnauthorizedException(WebClientResponseException webClientResponseException) {

	setWebClientResponseException(webClientResponseException);
    }

    public WebClientResponseException getWebClientResponseException() {

	return webClientResponseException;
    }

    public void setWebClientResponseException(WebClientResponseException webClientResponseException) {

	this.webClientResponseException = webClientResponseException;
    }
}
