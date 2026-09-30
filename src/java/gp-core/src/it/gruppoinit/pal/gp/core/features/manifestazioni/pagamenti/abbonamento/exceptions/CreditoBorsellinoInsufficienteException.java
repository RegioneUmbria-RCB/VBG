package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions;

public class CreditoBorsellinoInsufficienteException extends Exception {

    private static final long serialVersionUID = 7566552530411482259L;

    public CreditoBorsellinoInsufficienteException(String errore) {

	super(errore);
    }
}
