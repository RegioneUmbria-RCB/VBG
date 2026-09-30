package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcDestinazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcDestinazioniDAO extends BaseDAO<CcDestinazioni, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcDestinazioni> findAll(Integer firstResult, Integer maxResult);
}
