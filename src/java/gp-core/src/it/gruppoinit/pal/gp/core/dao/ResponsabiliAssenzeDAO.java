package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliAssenze;

import java.util.List;

/**
 * 
 * @author
 */
public interface ResponsabiliAssenzeDAO extends BaseDAO<ResponsabiliAssenze, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<ResponsabiliAssenze> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di assenze per il resposabile oridinadate asc per campo 'dal'
     * 
     * @param codice
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<ResponsabiliAssenze> findByResponsabile(Integer codice, Integer firstResult, Integer maxResult);
}
