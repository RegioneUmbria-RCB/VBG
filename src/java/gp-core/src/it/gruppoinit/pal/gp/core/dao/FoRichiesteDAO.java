package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoRichieste;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface FoRichiesteDAO extends BaseDAO<FoRichieste, PkId> {

    /**
     * Torna la lista delle richieste provenienti dal frontoffice ordinate per il campo data richiesta ASC
     */
    public List<FoRichieste> findAll(Integer firstResult, Integer maxResult);
}
