/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Espressioni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.domain.PkId;


/**
 * @author francol
 *
 */
public interface Dyn2EspressioniDAO extends BaseDAO<Dyn2Espressioni, PkId> {
    
    /**
     * Se esiste restituisce l'espressione che appartiene alla regola passata come primo argomento 
     * e che ha il progressivo specificato nel secondo argomento.
     * Se non esiste restituisce null.
     * @param regola
     * @param progressivo
     * @return
     */
    public Dyn2Espressioni finfByRegolaEProgressivo(Dyn2Regole regola, int progressivo);
    
    /**
     * Restituisce tutte le espressioni che effettuano delle verifiche sul valore del campo dinamico passato come argomento
     * @param campo
     * @return
     */
    public List<Dyn2Espressioni> findByCampoDinamico(Dyn2Campi campo);


}
