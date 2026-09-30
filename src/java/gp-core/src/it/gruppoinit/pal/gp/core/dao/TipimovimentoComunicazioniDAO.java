package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoComunicazioni;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipimovimentoComunicazioniDAO extends BaseDAO<TipimovimentoComunicazioni, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<TipimovimentoComunicazioni> findAll(Integer firstResult, Integer maxResult);
}
