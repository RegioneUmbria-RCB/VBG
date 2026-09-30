package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcCondizioniAttivita;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcCondizioniAttivitaDAO extends BaseDAO<CcCondizioniAttivita, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcCondizioniAttivita> findAll(Integer firstResult, Integer maxResult);
}
