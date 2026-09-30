package it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions;

public class ProtocollazioneAutorizzazioneException extends RuntimeException {

    public ProtocollazioneAutorizzazioneException(String message) {

	super(message);
    }

    public ProtocollazioneAutorizzazioneException(String message, Exception e) {

	super(message, e);
    }

    private static final long serialVersionUID = -2510611366170515087L;
}
