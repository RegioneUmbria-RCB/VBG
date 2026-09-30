/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.cart.mapping;


/**
 * Eccezione specifica per la gestione degli errori relativi alle mappature dei valori di VBG sugli id semantici della domanda CART.
 * @author francol
 *
 */
public class CartMappingException extends Exception {
    
    private String idSemantico;

    /**
     * 
     */
    private static final long serialVersionUID = -3245508933691600883L;

    /**
     * 
     */
    public CartMappingException() {

    }

    /**
     * @param message
     */
    public CartMappingException(String message, String idSemantico) {

	super(message);
	this.idSemantico = idSemantico;
    }

    /**
     * @param cause
     */
    public CartMappingException(Throwable cause, String idSemantico) {

	super(cause);
	this.idSemantico = idSemantico;
    }

    /**
     * @param message
     * @param cause
     */
    public CartMappingException(String message, Throwable cause, String idSemantico) {

	super(message, cause);
	this.idSemantico = idSemantico;
    }

    
    public String getIdSemantico() {
    
        return idSemantico;
    }

    
}
