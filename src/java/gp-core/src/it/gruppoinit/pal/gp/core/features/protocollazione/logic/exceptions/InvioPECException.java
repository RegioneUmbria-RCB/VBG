package it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions;

public class InvioPECException extends RuntimeException {

    public InvioPECException(String message, Exception exception) {

	super(message, exception);
    }

    private static final long serialVersionUID = 1005543802935802310L;
}
