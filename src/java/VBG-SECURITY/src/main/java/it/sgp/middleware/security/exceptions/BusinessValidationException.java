package it.sgp.middleware.security.exceptions;

public class BusinessValidationException extends RuntimeException {

    private static final long serialVersionUID = -1793890891083538248L;

    public BusinessValidationException() {

    }

    public BusinessValidationException(String message) {

	super(message);
    }

    public BusinessValidationException(Throwable cause) {

	super(cause);
    }

    public BusinessValidationException(String message, Throwable cause) {

	super(message, cause);
    }
}
