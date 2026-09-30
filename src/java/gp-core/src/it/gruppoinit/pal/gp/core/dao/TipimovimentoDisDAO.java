package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoDis;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipimovimentoDisDAO extends BaseDAO<TipimovimentoDis, PkId> {

    /**
     * @deprecated
     * @throws NotImplementedException
     * 
     */
    public List<TipimovimentoDis> findAll(Integer firstResult, Integer maxResult);
}
