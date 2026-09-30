package com.paevolution.appiogateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class HttpBadRequestException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -2307942442644272150L;
    private WebClientResponseException webClientResponseException;

    public HttpBadRequestException(String message) {

	super(message);
    }

    public HttpBadRequestException(WebClientResponseException webClientResponseException) {

	setWebClientResponseException(webClientResponseException);
    }

    public WebClientResponseException getWebClientResponseException() {

	return webClientResponseException;
    }

    public void setWebClientResponseException(WebClientResponseException webClientResponseException) {

	this.webClientResponseException = webClientResponseException;
    }
}
