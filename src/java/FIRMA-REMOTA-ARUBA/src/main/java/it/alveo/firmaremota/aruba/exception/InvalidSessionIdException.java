package it.alveo.firmaremota.aruba.exception;

public class InvalidSessionIdException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -2286164070248173072L;

    public static InvalidSessionIdException fromEmpty() {

	return new InvalidSessionIdException("Non è stato passato l'identificativo della sessione di firma");
    }

    public InvalidSessionIdException() {

	super();
    }

    public InvalidSessionIdException(String message) {

	super(message);
    }

    public InvalidSessionIdException(Throwable cause) {

	super(cause);
    }
}