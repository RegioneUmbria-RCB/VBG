/**
 * 
 */
package it.gruppoinit.pal.gp.pay.exception;

/**
 * Superclasse delle eccezioni relative alla logica business del nodo dei pagamenti. 
 * Consente di specificare anche un codice di errore e un boolean che indica se l'errore è ripristinabile con tentativi successivi
 * che sono informazioni utili nella scrittura degli esiti delle operazioni sulle posizioni debitorie
 * @author francol
 *
 */
public class PayException extends Exception {

    private static final long serialVersionUID = -3108262894159115886L;
    private String errorCode = null;
    private boolean resumable = false;

    public PayException() {

    }

    public PayException(String message) {

	super(message);
    }

    public PayException(String message, boolean resumable) {

	super(message);
	this.resumable = resumable;
    }

    public PayException(String message, boolean resumable, String errorCode) {

	super(message);
	this.errorCode = errorCode;
	this.resumable = resumable;
    }

    /**
     * @param cause
     */
    public PayException(Throwable cause) {

	super(cause);
    }

    public PayException(Throwable cause, boolean resumable) {

	super(cause);
	this.resumable = resumable;
    }

    public PayException(Throwable cause, boolean resumable, String errorCode) {

	super(cause);
	this.errorCode = errorCode;
	this.resumable = resumable;
    }

    public PayException(String message, Throwable cause) {

	super(message, cause);
    }

    public PayException(String message, Throwable cause, boolean resumable) {

	super(message, cause);
	this.resumable = resumable;
    }

    public PayException(String message, Throwable cause, boolean resumable, String errorCode) {

	super(message, cause);
	this.errorCode = errorCode;
	this.resumable = resumable;
    }

    public String getErrorCode() {

	return errorCode;
    }

    public void setErrorCode(String errorCode) {

	this.errorCode = errorCode;
    }

    public boolean isResumable() {

	return resumable;
    }

    public void setResumable(boolean resumable) {

	this.resumable = resumable;
    }
}
