package it.paevolution.fileconverter2.service;

public class DocumentConvertionException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -7765794775044338716L;

    public DocumentConvertionException() {

	super();
    }

    public DocumentConvertionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

	super(message, cause, enableSuppression, writableStackTrace);
    }

    public DocumentConvertionException(String message, Throwable cause) {

	super(message, cause);
    }

    public DocumentConvertionException(String message) {

	super(message);
    }

    public DocumentConvertionException(Throwable cause) {

	super(cause);
    }
}
