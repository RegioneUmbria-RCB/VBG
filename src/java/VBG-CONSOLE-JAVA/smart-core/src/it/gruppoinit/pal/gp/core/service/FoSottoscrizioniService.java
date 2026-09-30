package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoSottoscrizioniDAO;
import it.gruppoinit.pal.gp.core.domain.FoSottoscrizioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoSottoscrizioniService extends BaseService<FoSottoscrizioni, PkId> {

    /**
     * @see FoSottoscrizioniDAO#findAll(Integer, Integer)
     */
    public List<FoSottoscrizioni> findAll(Integer firstResult, Integer maxResult);
}
