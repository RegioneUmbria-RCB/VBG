package it.gruppoinit.pdd.exceptions;

public class PortaDelegataException extends Exception {

    private static final long serialVersionUID = -5652127788362515219L;

    public PortaDelegataException() {

	this("Errore generico nell'invocazione della porta delegata");
    }

    public PortaDelegataException(String messaggioErrore) {

	super(messaggioErrore);
    }

    public PortaDelegataException(Throwable causa) {

	super(causa);
    }

    public PortaDelegataException(String messaggio, Throwable causa) {

	super(messaggio, causa);
    }
}
