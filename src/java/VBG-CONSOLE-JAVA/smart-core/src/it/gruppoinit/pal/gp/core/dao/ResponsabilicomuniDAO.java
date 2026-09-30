/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.ResponsabilicomuniId;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface ResponsabilicomuniDAO extends BaseDAO<Responsabilicomuni, ResponsabilicomuniId> {

    /**
     * trova la lista dei comuni abilitati per l'operatore ordinati per comune
     * 
     * @param responsabile
     * @return
     */
    public List<Responsabilicomuni> findByOperatore(Responsabili responsabile);
}
