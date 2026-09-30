package it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions;

public class EseguiAccettazioneException extends Exception {

    private static final long serialVersionUID = -7014186354834003777L;

    public EseguiAccettazioneException(Exception e) {

	super(e);
    }

    public EseguiAccettazioneException(String message) {

	super(message);
    }
}
