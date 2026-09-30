package it.gruppoinit.pal.gp.core.exception;

public class OperatoreNonHaComuniConfiguratiException extends RuntimeException {

    private static final long serialVersionUID = -6761435884019998144L;

    public OperatoreNonHaComuniConfiguratiException() {

	super("Attenzione! L'utente non ha Comuni configurati.");
    }

    public OperatoreNonHaComuniConfiguratiException(String message, Throwable cause) {

	super(message, cause);
    }

    public OperatoreNonHaComuniConfiguratiException(String message) {

	super(message);
    }

    public OperatoreNonHaComuniConfiguratiException(Throwable cause) {

	super(cause);
    }
}
