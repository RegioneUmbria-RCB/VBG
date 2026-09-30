package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.SoftwareattiviDAO;
import it.gruppoinit.pal.gp.core.domain.Softwareattivi;
import it.gruppoinit.pal.gp.core.domain.SoftwareattiviId;
import it.gruppoinit.pal.gp.core.domain.helper.SoftwareattiviDTO;

import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface SoftwareattiviService extends BaseService<Softwareattivi, SoftwareattiviId> {

    /**
     * @see SoftwareattiviDAO#findAll(Integer, Integer)
     */
    public List<Softwareattivi> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna la lista dei software attivi escluso TT filtrando (opzionale) quelli solo attivi anche per il frontoffice
     * (attivoFo)
     * 
     * @return
     */
    public List<Softwareattivi> findAllAndExcludeTT(boolean isAttiviFO);

    /**
     * Ritorna la lista di SoftwareattiviDTO
     * 
     * @return
     */
    public List<SoftwareattiviDTO> findAllSoftwareattiviDTO();
}
