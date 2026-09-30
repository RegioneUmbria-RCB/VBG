package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CommedilizieTipologiedettDAO;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedett;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedettId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface CommedilizieTipologiedettService extends BaseService<CommedilizieTipologiedett, CommedilizieTipologiedettId> {

    /**
     * @see CommedilizieTipologiedettDAO#findAll(Integer, Integer)
     */
    public List<CommedilizieTipologiedett> findAll(Integer firstResult, Integer maxResult);

    public List<CommedilizieTipologiedett> findByTipologia(CommedilizieTipologie entity);

    /**
     * Trova la lista dei record che hanno COMMEDILIZIE_TIPOLOGIEDETT.TIPOMOVIMENTO = tipomovimento
     * 
     * @param tipomovimento
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<CommedilizieTipologiedett> findTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult);
}
