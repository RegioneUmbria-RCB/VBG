package it.sgp.middleware.security.exceptions;

public class InvalidLoginArgumentsException extends RuntimeException {

    private static final long serialVersionUID = 6083165571287774454L;

    public InvalidLoginArgumentsException() {

	super();
    }

    public InvalidLoginArgumentsException(String message, Throwable cause) {

	super(message, cause);
    }

    public InvalidLoginArgumentsException(String message) {

	super(message);
    }

    public InvalidLoginArgumentsException(Throwable cause) {

	super(cause);
    }
}
