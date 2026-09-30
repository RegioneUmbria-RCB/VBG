package it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions;

public class ProtocollaIstanzaException extends RuntimeException {

    private static final long serialVersionUID = 5094314076919576799L;

    public ProtocollaIstanzaException(String message, Exception e) {

	super(message, e);
    }

    public ProtocollaIstanzaException(String message) {

	super(message);
    }
}
