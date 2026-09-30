package com.paevolution.appiogateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ResponseStatus(code = HttpStatus.TOO_MANY_REQUESTS)
public class HttpTooManyRequestsException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 4280779104377781423L;
    private WebClientResponseException webClientResponseException;

    public HttpTooManyRequestsException(String message) {

	super(message);
    }

    public HttpTooManyRequestsException(WebClientResponseException webClientResponseException) {

	setWebClientResponseException(webClientResponseException);
    }

    public WebClientResponseException getWebClientResponseException() {

	return webClientResponseException;
    }

    public void setWebClientResponseException(WebClientResponseException webClientResponseException) {

	this.webClientResponseException = webClientResponseException;
    }
}
