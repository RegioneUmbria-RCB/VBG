package it.gruppoinit.pal.gp.core.rest.client;

public class DSSClientException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 8805858822659318849L;

    public DSSClientException(Exception e) {

	super(e);
    }

    public DSSClientException(String messaggioErrore) {

	super(messaggioErrore);
    }

    public DSSClientException(Throwable cause) {

	super(cause);
    }

    public DSSClientException(String messaggioErrore, Exception e) {

	super(messaggioErrore, e);
    }
}
