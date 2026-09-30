package com.paevolution.appiogateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataIntegrationException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 7778462354036682057L;

    public DataIntegrationException(String message) {

	super(message);
    }

    public DataIntegrationException(String message, Throwable cause) {

	super(message, cause);
    }
}
