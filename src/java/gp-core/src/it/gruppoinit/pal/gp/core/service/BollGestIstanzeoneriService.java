package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.BollGestIstanzeoneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollGestIstanzeoneriDAO;

/**
 * 
 * @author
 */
public interface BollGestIstanzeoneriService extends BaseService<BollGestIstanzeoneri, PkId> {

    /**
     * @see BollGestIstanzeoneriDAO#findAll(Integer, Integer)
     */
    public List<BollGestIstanzeoneri> findAll(Integer firstResult, Integer maxResult);
}
