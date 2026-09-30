package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.ODestinazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface ODestinazioniDAO extends BaseDAO<ODestinazioni, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<ODestinazioni> findAll(Integer firstResult, Integer maxResult);
}
