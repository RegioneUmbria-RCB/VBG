package it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions;

public class RicercaFascicoliException extends RuntimeException {

    public RicercaFascicoliException(String message, Exception e) {

	super(message, e);
    }

    public RicercaFascicoliException(String message) {

	super(message);
    }

    public RicercaFascicoliException(Exception e) {

	super(e);
    }

    private static final long serialVersionUID = 4607751860941648057L;
}
