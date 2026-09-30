package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiorario;

import java.util.List;

/**
 * 
 * @author lucap
 */
public interface TipiorarioDAO extends BaseDAO<Tipiorario, PkId> {

    /**
     * Restituisce le tipologie di orario (filtrando per idcomune e software) ordinandole per il campo descrizione asc
     * 
     */
    public List<Tipiorario> findAll(Integer firstResult, Integer maxResult);

    /**
     * Restituisce una lista di Tipiorario filtrati per idcomune, software, toDescrizione, toPeriododa, toPeriodoa.
     * Ordinati per toDescrizione.
     * 
     * @param entity
     * @return
     */
    public List<Tipiorario> findByFilter(Tipiorario entity);
}
