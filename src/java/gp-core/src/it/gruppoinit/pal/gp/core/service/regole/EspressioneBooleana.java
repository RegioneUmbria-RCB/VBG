/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.regole;

import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;

/**
 * Qualunque elemento che nell'elaborazione di una regola restituisca un valore booleano deve implementare questa
 * interfaccia.
 * 
 * @author francol
 * 
 */
public interface EspressioneBooleana {

    public Boolean valutaBoolean() throws Dyn2RegoleSyntaxError;
}
