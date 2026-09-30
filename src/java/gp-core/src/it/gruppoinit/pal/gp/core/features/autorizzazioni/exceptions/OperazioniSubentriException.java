package it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions;

public class OperazioniSubentriException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 464156845216220384L;

    public OperazioniSubentriException(String message, Throwable throwable) {

	super(message, throwable);
    }

    public OperazioniSubentriException(Throwable throwable) {

	super(throwable);
    }

    public OperazioniSubentriException(String message) {

	super(message);
    }
}
