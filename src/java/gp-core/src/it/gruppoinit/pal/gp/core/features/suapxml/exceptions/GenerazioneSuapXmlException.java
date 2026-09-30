package it.gruppoinit.pal.gp.core.features.suapxml.exceptions;

public class GenerazioneSuapXmlException extends RuntimeException {

    private static final long serialVersionUID = 4861498134707121274L;

    public GenerazioneSuapXmlException(String message) {

	super(message);
    }

    public GenerazioneSuapXmlException(Exception e) {

	super(e);
    }
}
