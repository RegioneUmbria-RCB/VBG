package it.alveo.firmaremota.aruba.exception;

public class FirmaException extends RuntimeException {

    private static final long serialVersionUID = -2317078158705496443L;

    public FirmaException(String message) {

	super(message);
    }

    public FirmaException(Throwable cause) {

	super(cause);
    }
}
