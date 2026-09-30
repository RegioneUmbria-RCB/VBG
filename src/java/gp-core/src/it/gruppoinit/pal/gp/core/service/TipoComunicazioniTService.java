package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipoComunicazioniTDAO;
import it.gruppoinit.pal.gp.core.domain.TipoComunicazioniT;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipoComunicazioniTService extends BaseService<TipoComunicazioniT, String> {

    /**
     * @see TipoComunicazioniTDAO#findAll(Integer, Integer)
     */
    public List<TipoComunicazioniT> findAll(Integer firstResult, Integer maxResult);
}
