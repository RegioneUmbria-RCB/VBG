package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ODestinazioniDAO;
import it.gruppoinit.pal.gp.core.domain.ODestinazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface ODestinazioniService extends BaseService<ODestinazioni, PkId> {

    /**
     * @see ODestinazioniDAO#findAll(Integer, Integer)
     */
    public List<ODestinazioni> findAll(Integer firstResult, Integer maxResult);
}
