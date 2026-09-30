package it.sgp.middleware.security.exceptions;

public class InvalidCredentialsException extends RuntimeException {

    private static final long serialVersionUID = -6829189142734229565L;

    public InvalidCredentialsException() {

	super();
    }

    public InvalidCredentialsException(String message, Throwable cause) {

	super(message, cause);
    }

    public InvalidCredentialsException(String message) {

	super(message);
    }

    public InvalidCredentialsException(Throwable cause) {

	super(cause);
    }
}
