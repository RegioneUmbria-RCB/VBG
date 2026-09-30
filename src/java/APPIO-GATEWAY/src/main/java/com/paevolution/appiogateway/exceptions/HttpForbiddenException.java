package com.paevolution.appiogateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class HttpForbiddenException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -3939590693520380658L;
    private WebClientResponseException webClientResponseException;

    public HttpForbiddenException(WebClientResponseException webClientResponseException) {

	setWebClientResponseException(webClientResponseException);
    }

    public HttpForbiddenException(String msg) {

	super(msg);
    }

    public WebClientResponseException getWebClientResponseException() {

	return webClientResponseException;
    }

    public void setWebClientResponseException(WebClientResponseException webClientResponseException) {

	this.webClientResponseException = webClientResponseException;
    }
}
