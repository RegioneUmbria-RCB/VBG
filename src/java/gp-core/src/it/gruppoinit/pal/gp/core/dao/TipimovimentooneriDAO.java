package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Tipimovimentooneri;
import it.gruppoinit.pal.gp.core.domain.TipimovimentooneriId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface TipimovimentooneriDAO extends BaseDAO<Tipimovimentooneri, TipimovimentooneriId> {

    /**
     * Restituisce una lista di tipi movimenti oneri filtrati per id comune
     * 
     */
    public List<Tipimovimentooneri> findAll(Integer firstResult, Integer maxResult);
}
