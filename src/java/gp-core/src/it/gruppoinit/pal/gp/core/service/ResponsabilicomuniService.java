/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ResponsabilicomuniDAO;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.ResponsabilicomuniId;

import java.util.List;

/**
 * @author francescop
 * @author Luca Proietti
 * 
 */
public interface ResponsabilicomuniService extends BaseService<Responsabilicomuni, ResponsabilicomuniId> {

    /**
     * @see ResponsabilicomuniDAO#findByOperatore(Responsabili)
     * 
     * @return
     */
    public List<Responsabilicomuni> findByOperatore(Responsabili responsabile);

    /**
     * Cancella tutti i record di responsabiliComuni di un responsabile
     * 
     * @param entity
     */
    public void deleteByResponsabile(Responsabili entity);
}
