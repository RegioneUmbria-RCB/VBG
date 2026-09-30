package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcTabellaCaratteristDAO;
import it.gruppoinit.pal.gp.core.domain.CcTabellaCaratterist;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcTabellaCaratteristService extends BaseService<CcTabellaCaratterist, PkId> {

    /**
     * @see CcTabellaCaratteristDAO#findAll(Integer, Integer)
     */
    public List<CcTabellaCaratterist> findAll(Integer firstResult, Integer maxResult);
}
