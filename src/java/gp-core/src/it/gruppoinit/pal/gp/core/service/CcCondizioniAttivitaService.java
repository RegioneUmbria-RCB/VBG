package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcCondizioniAttivitaDAO;
import it.gruppoinit.pal.gp.core.domain.CcCondizioniAttivita;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcCondizioniAttivitaService extends BaseService<CcCondizioniAttivita, PkId> {

    /**
     * @see CcCondizioniAttivitaDAO#findAll(Integer, Integer)
     */
    public List<CcCondizioniAttivita> findAll(Integer firstResult, Integer maxResult);
}
