/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.ResponsabilisoftwareId;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface ResponsabilisoftwareService extends BaseService<Responsabilisoftware, ResponsabilisoftwareId> {

    /**
     * Cancella tutti i record di responsabiliSoftware di un responsabile
     * 
     * @param entity
     */
    public void deleteByResponsabile(Responsabili entity);

    /**
     * Metodo che ricerca in ResponsabiliSoftware per codiceresponsabile
     * 
     * @param responsabili
     * @return
     */
    public List<Responsabilisoftware> findByResponsabile(Responsabili responsabili);

    /**
     * Metodo che ricerca i ResponsabiliSoftware di un responsabile tramite like sulla proprietà descrizione del
     * software
     * 
     * @param responsabili
     * @param software
     * @return
     */
    public List<Responsabilisoftware> findBySoftware(Responsabili responsabili, Software software);

    /**
     * Verifica se il responsabile ha il software configurato
     * 
     * @param codicecresponsabile
     * @param software
     * @return
     */
    public boolean checkByResponsabileAndSoftware(Integer codicecresponsabile, String software);
}
