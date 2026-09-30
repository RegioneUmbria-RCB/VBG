package it.gruppoinit.pal.gp.core.service.exception;

public class OperazioniAutomaticheException extends Exception {

    private static final long serialVersionUID = -7176149622644772438L;

    public OperazioniAutomaticheException(String message, Throwable cause) {

	super(message, cause);
    }

    public OperazioniAutomaticheException(String message) {

	super(message);
    }

    public OperazioniAutomaticheException(Throwable cause) {

	super(cause);
    }
}
