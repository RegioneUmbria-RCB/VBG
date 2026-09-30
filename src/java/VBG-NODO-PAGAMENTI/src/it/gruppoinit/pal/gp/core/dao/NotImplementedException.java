package it.gruppoinit.pal.gp.core.dao;

public class NotImplementedException extends RuntimeException {

    private static final long serialVersionUID = -5898664408389030958L;

    public NotImplementedException() {

	super();
    }

    public NotImplementedException(String message, Throwable cause) {

	super(message, cause);
    }

    public NotImplementedException(String message) {

	super(message);
    }

    public NotImplementedException(Throwable cause) {

	super(cause);
    }
}
