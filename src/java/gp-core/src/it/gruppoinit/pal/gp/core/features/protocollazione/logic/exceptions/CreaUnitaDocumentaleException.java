package it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions;

public class CreaUnitaDocumentaleException extends RuntimeException {

    public CreaUnitaDocumentaleException(String message) {

	super(message);
    }

    public CreaUnitaDocumentaleException(String message, Exception e) {

	super(message, e);
    }

    private static final long serialVersionUID = -802853290793310469L;
}
