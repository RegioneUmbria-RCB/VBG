package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions;

public class BorsellinoException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6048396102230191795L;

    public BorsellinoException(String errore) {

	super(errore);
    }

    public BorsellinoException() {

	super();
    }

    public BorsellinoException(String message, Throwable cause) {

	super(message, cause);
    }

    public BorsellinoException(Throwable cause) {

	super(cause);
    }
}
