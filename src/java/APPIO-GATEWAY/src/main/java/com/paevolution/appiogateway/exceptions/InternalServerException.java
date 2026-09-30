package com.paevolution.appiogateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -9047493870750201977L;
    private WebClientResponseException webClientResponseException;

    public InternalServerException(String exception) {

	super(exception);
    }

    public InternalServerException(WebClientResponseException webClientResponseException) {

	setWebClientResponseException(webClientResponseException);
    }

    public WebClientResponseException getWebClientResponseException() {

	return webClientResponseException;
    }

    public void setWebClientResponseException(WebClientResponseException webClientResponseException) {

	this.webClientResponseException = webClientResponseException;
    }
}
