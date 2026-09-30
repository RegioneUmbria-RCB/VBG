/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaistanza;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface TipologiaistanzaDAO extends BaseDAO<Tipologiaistanza, PkId> {

    /**
     * Restituisce tutte le Tipologie delle Istanze (filtrando per idcomune e software) ordinandole per il campo
     * descrizione ascendente
     */
    public List<Tipologiaistanza> findAll(Integer firstResult, Integer maxResult);
}
