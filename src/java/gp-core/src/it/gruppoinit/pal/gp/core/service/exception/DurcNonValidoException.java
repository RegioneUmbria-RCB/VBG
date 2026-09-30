package it.gruppoinit.pal.gp.core.service.exception;

public class DurcNonValidoException extends RuntimeException {

    private static final long serialVersionUID = 7748830038141234635L;

    public DurcNonValidoException(String messaggioErrore) {

	super(messaggioErrore);
    }

    public DurcNonValidoException() {

	super();
    }

    public DurcNonValidoException(String message, Throwable cause) {

	super(message, cause);
    }

    public DurcNonValidoException(Throwable cause) {

	super(cause);
    }
}
