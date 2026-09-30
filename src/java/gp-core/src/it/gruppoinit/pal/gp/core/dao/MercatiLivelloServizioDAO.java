package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MercatiLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface MercatiLivelloServizioDAO extends BaseDAO<MercatiLivelloServizio, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<MercatiLivelloServizio> findAll(Integer firstResult, Integer maxResult);
}
