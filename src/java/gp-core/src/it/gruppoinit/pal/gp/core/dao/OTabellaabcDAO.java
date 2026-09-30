package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OTabellaabc;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OTabellaabcDAO extends BaseDAO<OTabellaabc, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OTabellaabc> findAll(Integer firstResult, Integer maxResult);
}
