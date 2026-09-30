package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcDestinazioniDAO;
import it.gruppoinit.pal.gp.core.domain.CcDestinazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcDestinazioniService extends BaseService<CcDestinazioni, PkId> {

    /**
     * @see CcDestinazioniDAO#findAll(Integer, Integer)
     */
    public List<CcDestinazioni> findAll(Integer firstResult, Integer maxResult);
}
