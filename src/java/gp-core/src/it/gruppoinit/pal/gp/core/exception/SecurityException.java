package it.gruppoinit.pal.gp.core.exception;

public class SecurityException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 7487257503576053901L;

    public SecurityException() {

	super();
    }

    public SecurityException(String message, Throwable cause) {

	super(message, cause);
    }

    public SecurityException(String message) {

	super(message);
    }

    public SecurityException(Throwable cause) {

	super(cause);	
    }
}
