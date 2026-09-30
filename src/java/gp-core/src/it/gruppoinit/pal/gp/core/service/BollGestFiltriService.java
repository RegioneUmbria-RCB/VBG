package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.BollGestFiltri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollGestFiltriDAO;

import java.util.List;

/**
 * 
 * @author 
 */
public interface BollGestFiltriService extends BaseService<BollGestFiltri, PkId> {

    /**
     * @see BollGestFiltriDAO#findAll(Integer, Integer)
     */
    public List<BollGestFiltri> findAll(Integer firstResult, Integer maxResult);
}
