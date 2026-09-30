package it.gruppoinit.sigeprosecurity.ws.exceptions;

public class SecurityException extends RuntimeException {

    private static final long serialVersionUID = -7348579896507924505L;

    public SecurityException(String message) {

	super(message);
    }

    public SecurityException(Exception ex) {

	super(ex);
    }
}
