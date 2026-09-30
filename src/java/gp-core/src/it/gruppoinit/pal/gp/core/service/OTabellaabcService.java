package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OTabellaabcDAO;
import it.gruppoinit.pal.gp.core.domain.OTabellaabc;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OTabellaabcService extends BaseService<OTabellaabc, PkId> {

    /**
     * @see OTabellaabcDAO#findAll(Integer, Integer)
     */
    public List<OTabellaabc> findAll(Integer firstResult, Integer maxResult);
}
