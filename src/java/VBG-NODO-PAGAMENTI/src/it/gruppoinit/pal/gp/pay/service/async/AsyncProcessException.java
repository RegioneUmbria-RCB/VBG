package it.gruppoinit.pal.gp.pay.service.async;

public class AsyncProcessException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -8498056882203080185L;

    public AsyncProcessException() {

	super();
    }

    public AsyncProcessException(String message, Throwable cause) {

	super(message, cause);
    }

    public AsyncProcessException(String message) {

	super(message);
    }

    public AsyncProcessException(Throwable cause) {

	super(cause);
    }
}
