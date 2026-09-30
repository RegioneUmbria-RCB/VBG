package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.VwIstanzesoggetticollegati;
import it.gruppoinit.pal.gp.core.domain.VwIstanzesoggetticollegatiId;
import it.gruppoinit.pal.gp.core.domain.helper.VwIstanzesoggetticollegatiDTO;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface VwIstanzesoggetticollegatiDAO extends BaseDAO<VwIstanzesoggetticollegati, VwIstanzesoggetticollegatiId> {

    /**
     * 
     * 
     */
    public List<VwIstanzesoggetticollegati> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di VwIstanzesoggetticollegati filtrati per anagrafe e software
     */
    public List<VwIstanzesoggetticollegatiDTO> findByRichiedenteAndSoftware(Anagrafe richiedente, Software software);
}
