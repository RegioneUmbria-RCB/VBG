package it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions;

public class AggiungiAllegatiException extends RuntimeException {

    private static final long serialVersionUID = -9165973028415473755L;

    public AggiungiAllegatiException(String message, Exception exception) {

	super(message, exception);
    }
}
