/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ResponsabilicomuniDAO;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.ResponsabilicomuniId;

import java.util.List;

/**
 * @author fabrizioc
 * 
 */
public interface ResponsabilicomuniService extends BaseService<Responsabilicomuni, ResponsabilicomuniId> {

    /**
     * @see ResponsabilicomuniDAO#findByOperatore(Responsabili)
     * 
     * @return
     */
    public List<Responsabilicomuni> findByResponsabile(Responsabili responsabile);

    /**
     * 
     * @param comune
     * @return
     */
    public List<Responsabilicomuni> findByComune(Comuni comune);
}
