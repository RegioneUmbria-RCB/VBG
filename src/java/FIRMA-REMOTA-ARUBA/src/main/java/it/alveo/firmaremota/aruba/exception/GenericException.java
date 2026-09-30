package it.alveo.firmaremota.aruba.exception;

public class GenericException extends RuntimeException {

    private static final long serialVersionUID = -9025206886911455739L;

    public GenericException(String message) {

	super(message);
    }

    public GenericException(Throwable cause) {

	super(cause);
    }

    public GenericException(String message, Throwable cause) {

	super(message, cause);
    }
}
