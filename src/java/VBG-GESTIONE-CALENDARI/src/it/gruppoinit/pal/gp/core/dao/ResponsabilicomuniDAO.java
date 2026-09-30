/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.ResponsabilicomuniId;

import java.util.List;

/**
 * @author fabrizioc
 * 
 */
public interface ResponsabilicomuniDAO extends BaseDAO<Responsabilicomuni, ResponsabilicomuniId> {

    /**
     * lista dei comuni abilitati per l'operatore ordinati per COMUUNE ASC
     * 
     * @param responsabile
     * @return
     */
    public List<Responsabilicomuni> findByResponsabile(Responsabili responsabile);

    /**
     * lista dei responsabili abilitati per il comune ordinati per RESPONSABILE ASC
     * 
     * @param comune
     * @return
     */
    public List<Responsabilicomuni> findByComune(Comuni comune);
}
