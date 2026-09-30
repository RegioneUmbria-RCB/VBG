package it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions;

public class RecuperaClassificheException extends RuntimeException {

    public RecuperaClassificheException(String message, Exception e) {

	super(message, e);
    }

    private static final long serialVersionUID = 5625589299063384430L;
}
