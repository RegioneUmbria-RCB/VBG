/**
 * 
 */
package it.gruppoinit.pal.gp.pay.tracciati;

/**
 * Eccezion generata quando si verificano errori formali nella lettura o scrittura di un tracciato record.
 * @author Franco.Leone
 *
 */
public class TracciatoRecordException extends RuntimeException {

    private static final long serialVersionUID = -383804741787792279L;

    public TracciatoRecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

	super(message, cause, enableSuppression, writableStackTrace);
    }

    public TracciatoRecordException(String message, Throwable cause) {

	super(message, cause);
    }

    public TracciatoRecordException(String message) {

	super(message);
    }

    public TracciatoRecordException(Throwable cause) {

	super(cause);
    }
}
