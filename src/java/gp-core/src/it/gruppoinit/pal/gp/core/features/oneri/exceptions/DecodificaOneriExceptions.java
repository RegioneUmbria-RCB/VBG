package it.gruppoinit.pal.gp.core.features.oneri.exceptions;

public class DecodificaOneriExceptions extends RuntimeException {

    private static final long serialVersionUID = -3678469470046441892L;

    public DecodificaOneriExceptions(Exception e) {

	super(e);
    }

    public DecodificaOneriExceptions(String messaggioErrore) {

	super(messaggioErrore);
    }

    public DecodificaOneriExceptions(Throwable cause) {

	super(cause);
    }
}
