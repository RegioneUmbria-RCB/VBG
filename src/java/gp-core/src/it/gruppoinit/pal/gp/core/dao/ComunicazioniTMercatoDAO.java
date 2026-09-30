package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.ComunicazioniTMercato;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface ComunicazioniTMercatoDAO extends BaseDAO<ComunicazioniTMercato, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<ComunicazioniTMercato> findAll(Integer firstResult, Integer maxResult);
}
