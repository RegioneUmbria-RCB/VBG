package it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions;

public class LeggiProtocolloException extends RuntimeException {

    public LeggiProtocolloException(String message) {

	super(message);
    }

    public LeggiProtocolloException(String message, Exception e) {

	super(message, e);
    }

    private static final long serialVersionUID = -7570999558494400825L;
}
