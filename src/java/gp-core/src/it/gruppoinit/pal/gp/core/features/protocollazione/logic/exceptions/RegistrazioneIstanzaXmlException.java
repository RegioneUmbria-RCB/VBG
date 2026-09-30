package it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions;

public class RegistrazioneIstanzaXmlException extends RuntimeException {

    public RegistrazioneIstanzaXmlException(String message) {

	super(message);
    }

    public RegistrazioneIstanzaXmlException(String message, Exception exception) {

	super(message, exception);
    }

    public RegistrazioneIstanzaXmlException(Throwable cause) {

	super(cause);
    }

    private static final long serialVersionUID = -1211452306455877L;
}
