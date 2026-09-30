package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.TipoComunicazioniT;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipoComunicazioniTDAO extends BaseDAO<TipoComunicazioniT, String> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<TipoComunicazioniT> findAll(Integer firstResult, Integer maxResult);
}
