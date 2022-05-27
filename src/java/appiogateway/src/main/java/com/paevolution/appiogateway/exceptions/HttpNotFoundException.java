package com.paevolution.appiogateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class HttpNotFoundException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -8332858974536946808L;
    private WebClientResponseException webClientResponseException;

    public HttpNotFoundException(String message) {

	super(message);
    }

    public HttpNotFoundException(WebClientResponseException webClientResponseException) {

	setWebClientResponseException(webClientResponseException);
    }

    public WebClientResponseException getWebClientResponseException() {

	return webClientResponseException;
    }

    public void setWebClientResponseException(WebClientResponseException webClientResponseException) {

	this.webClientResponseException = webClientResponseException;
    }
}
