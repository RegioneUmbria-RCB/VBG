package it.gruppoinit.pal.gp.core.exceptions;


public class PkIdGenerationException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 7014093281247334360L;

    public PkIdGenerationException() {

	super();
    }

    public PkIdGenerationException(String message) {

	super(message);
    }

    public PkIdGenerationException(Throwable cause) {

	super(cause);
    }

    public PkIdGenerationException(String message, Throwable cause) {

	super(message, cause);
    }

    public PkIdGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

	super(message, cause, enableSuppression, writableStackTrace);
    }
}
