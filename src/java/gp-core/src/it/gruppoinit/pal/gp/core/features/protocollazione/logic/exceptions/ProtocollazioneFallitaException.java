package it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions;

public class ProtocollazioneFallitaException extends RuntimeException {

    private static final long serialVersionUID = -849344451625763461L;

    public ProtocollazioneFallitaException(Exception e) {

	super(e);
    }

    public ProtocollazioneFallitaException(String message) {

	super(message);
    }

    public ProtocollazioneFallitaException(String message, Exception e) {

	super(message, e);
    }
}
