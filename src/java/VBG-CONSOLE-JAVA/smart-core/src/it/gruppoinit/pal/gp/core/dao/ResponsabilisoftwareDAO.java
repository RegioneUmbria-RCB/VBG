/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.ResponsabilisoftwareId;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface ResponsabilisoftwareDAO extends BaseDAO<Responsabilisoftware, ResponsabilisoftwareId> {

    /**
     * Metodo che ricerca in ResponsabiliSoftware per codiceresponsabile
     * 
     * @param responsabili
     * @return
     */
    public List<Responsabilisoftware> findByResponsabile(Responsabili responsabili);
}
