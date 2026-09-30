package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OTabelladDAO;
import it.gruppoinit.pal.gp.core.domain.OTabellad;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OTabelladService extends BaseService<OTabellad, PkId> {

    /**
     * @see OTabelladDAO#findAll(Integer, Integer)
     */
    public List<OTabellad> findAll(Integer firstResult, Integer maxResult);
}
