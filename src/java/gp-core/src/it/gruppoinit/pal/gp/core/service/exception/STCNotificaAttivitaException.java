package it.gruppoinit.pal.gp.core.service.exception;

public class STCNotificaAttivitaException extends Exception {

    private static final long serialVersionUID = -562826451925192061L;

    public STCNotificaAttivitaException(String message) {

	super(message);
    }

    public STCNotificaAttivitaException(Throwable cause) {

	super(cause);
    }

    public STCNotificaAttivitaException(String message, Throwable cause) {

	super(message, cause);
    }
}
