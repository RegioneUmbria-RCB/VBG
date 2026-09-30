package it.gruppoinit.pal.gp.core.service.exception;

public class RemoteCallException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -8403906198878786818L;

    public RemoteCallException() {

	super();
    }

    public RemoteCallException(String message, Throwable throwable) {

	super(message, throwable);
    }

    public RemoteCallException(String message) {

	super(message);
    }

    public RemoteCallException(Throwable throwable) {

	super(throwable);
    }
}
