package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IstanzeRi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeRiDAO extends BaseDAO<IstanzeRi, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IstanzeRi> findAll(Integer firstResult, Integer maxResult);
}
