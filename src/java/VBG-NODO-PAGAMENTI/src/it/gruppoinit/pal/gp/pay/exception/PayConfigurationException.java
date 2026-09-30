/**
 * 
 */
package it.gruppoinit.pal.gp.pay.exception;

/**
 * Eccezione lanciata in caso di errata o mancante configurazione del nodo dei pagamenti
 * 
 * @author francol
 *
 */
public class PayConfigurationException extends PayException {

    private static final long serialVersionUID = 2797282177386402209L;

    public PayConfigurationException() {

	super();
    }

    public PayConfigurationException(String message) {

	super(message);
    }

    public PayConfigurationException(String message, Throwable cause) {

	super(message, cause);
    }

    public PayConfigurationException(Throwable cause) {

	super(cause);
    }
}
