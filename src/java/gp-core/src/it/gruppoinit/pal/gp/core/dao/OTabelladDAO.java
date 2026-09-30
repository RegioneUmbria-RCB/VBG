package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OTabellad;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OTabelladDAO extends BaseDAO<OTabellad, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OTabellad> findAll(Integer firstResult, Integer maxResult);
}
