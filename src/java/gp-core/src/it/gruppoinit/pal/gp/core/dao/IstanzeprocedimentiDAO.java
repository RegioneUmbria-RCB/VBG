/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.IstanzeprocedimentiId;

/**
 * @author francescop
 * 
 */
public interface IstanzeprocedimentiDAO extends BaseDAO<Istanzeprocedimenti, IstanzeprocedimentiId> {

    /**
     * Torna la lista di istanzeprocedimenti dell'istanza ordinati per tipifamiglieendo.ordine ASC
     * 
     * @param istanze
     * @return
     */
    public List<Istanzeprocedimenti> findByIstanze(Istanze istanze);
}
