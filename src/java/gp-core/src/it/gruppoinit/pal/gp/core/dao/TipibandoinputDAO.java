/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibando;
import it.gruppoinit.pal.gp.core.domain.Tipibandoinput;

import java.util.List;

/**
 * @author lucap
 * @author francescop
 */
public interface TipibandoinputDAO extends BaseDAO<Tipibandoinput, PkId> {

    public List<Tipibandoinput> findTipiBanInp(Tipibandoinput tipibandoinput);
    /**
     * Record ordinati per etichetta
     * @param tipibando
     * @return
     */
    public List<Tipibandoinput> findByFilterTipobando(Tipibando tipibando);
}
