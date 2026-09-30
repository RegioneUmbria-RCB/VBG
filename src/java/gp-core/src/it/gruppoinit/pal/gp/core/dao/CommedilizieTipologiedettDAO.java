package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedett;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedettId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface CommedilizieTipologiedettDAO extends BaseDAO<CommedilizieTipologiedett, CommedilizieTipologiedettId> {

    /**
     * torna la lista dei dettagli tipologia
     * 
     */
    public List<CommedilizieTipologiedett> findAll(Integer firstResult, Integer maxResult);

    public List<CommedilizieTipologiedett> findByTipologia(CommedilizieTipologie tipologia);
}
