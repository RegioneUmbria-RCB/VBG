package it.gruppoinit.pal.gp.core.exception;

public class SessionTimeoutException extends RuntimeException {

    private static final long serialVersionUID = 489069160476687607L;

    public SessionTimeoutException() {

	super();
    }

    public SessionTimeoutException(String message, Throwable cause) {

	super(message, cause);
    }

    public SessionTimeoutException(String message) {

	super(message);
    }

    public SessionTimeoutException(Throwable cause) {

	super(cause);
    }
}
