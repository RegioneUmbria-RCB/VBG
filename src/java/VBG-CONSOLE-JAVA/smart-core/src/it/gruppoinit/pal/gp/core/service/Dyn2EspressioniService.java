/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Dyn2Espressioni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.domain.PkId;


/**
 * @author francol
 *
 */
public interface Dyn2EspressioniService extends BaseService<Dyn2Espressioni, PkId> {
    
    public Dyn2Espressioni finfByRegolaEProgressivo(Dyn2Regole regola, int progressivo);
}
