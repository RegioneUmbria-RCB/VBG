package it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions;

public class ProtocollazioneMovimentoException extends Exception {

    private static final long serialVersionUID = 753463422568767233L;

    public ProtocollazioneMovimentoException(String message, Exception e) {

	super(message, e);
    }
}
