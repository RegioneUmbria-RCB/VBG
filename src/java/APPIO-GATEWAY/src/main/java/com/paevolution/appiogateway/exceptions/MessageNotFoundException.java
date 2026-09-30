package com.paevolution.appiogateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MessageNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -4103618989418153115L;

    public MessageNotFoundException(String exception) {

	super(exception);
    }
}
