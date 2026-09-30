package it.gruppoinit.pal.gp.core.features.alfresco.exception;

public class ECMException extends Exception {

    private static final long serialVersionUID = 1850143310406054236L;

    public ECMException() {

	super();
    }

    public ECMException(String message, Throwable cause) {

	super(message, cause);
    }

    public ECMException(String message) {

	super(message);
    }

    public ECMException(Throwable cause) {

	super(cause);
    }
}