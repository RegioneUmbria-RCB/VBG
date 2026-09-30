/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.regole;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService.OperatoreLogicoEnum;


/**
 * Superclasse di tutti gli operatori logici
 * @author francol
 *
 */
public abstract class AbstractOperatoreLogico implements OperatoreLogico {

    private static final Logger log = LoggerFactory.getLogger(OperatoreLogicoAnd.class);
    
    protected OperatoreLogicoEnum tipoOperatore;

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.regole.OperatoreLogico#eseguiOperazioneLogica(java.lang.Boolean, java.lang.Boolean)
     */
    @Override
    public abstract Boolean eseguiOperazioneLogica(EspressioneBooleana expr1, EspressioneBooleana expr2) throws Dyn2RegoleSyntaxError;
    
    protected void checkNull(EspressioneBooleana value)throws Dyn2RegoleSyntaxError{
	
	if (value == null || value.valutaBoolean() == null) {
	    log.error("checkNull - l'operatore logico {} non può essere applicato a valori nulli.", new Object[] { this.tipoOperatore });
	    Dyn2RegoleSyntaxError err = new Dyn2RegoleSyntaxError("dyn2regole.error.invalid_type_operatorelogico");
	    err.addWrongExpressionProperty("operatoreLogico");
	    throw err;
	}
    }
    
    public OperatoreLogicoEnum getTipoOperatore(){
	
	return tipoOperatore;
    }
}
