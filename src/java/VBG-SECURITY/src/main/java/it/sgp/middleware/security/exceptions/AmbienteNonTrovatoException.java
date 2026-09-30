package it.sgp.middleware.security.exceptions;

public class AmbienteNonTrovatoException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -7438541168789011081L;

    public AmbienteNonTrovatoException() {

	super();
    }

    public AmbienteNonTrovatoException(String message, Throwable cause) {

	super(message, cause);
    }

    public AmbienteNonTrovatoException(String message) {

	super(message);
    }

    public AmbienteNonTrovatoException(Throwable cause) {

	super(cause);
    }
}
