/**
 * 
 */
package it.gruppoinit.pal.gp.pay.exception;

/**
 * Eccezione generata nei casi di invocazione dei servizi di pagamento con dati in input non validi
 * 
 * @author francol
 *
 */
public class PayInvalidRequestException extends PayException {

    /**
     * 
     */
    private static final long serialVersionUID = 5392402192591605103L;

    public PayInvalidRequestException() {

    }

    /**
     * @param message
     */
    public PayInvalidRequestException(String message) {

	super(message);
    }

    /**
     * @param cause
     */
    public PayInvalidRequestException(Throwable cause) {

	super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public PayInvalidRequestException(String message, Throwable cause) {

	super(message, cause);
    }
}
