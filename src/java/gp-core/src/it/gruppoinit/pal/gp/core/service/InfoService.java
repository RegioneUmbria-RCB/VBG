package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.InfoDAO;
import it.gruppoinit.pal.gp.core.domain.Info;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author lucap
 */
public interface InfoService extends BaseService<Info, PkId> {

    /**
     * @see InfoDAO#findAll(Integer, Integer)
     */
    public List<Info> findAll(Integer firstResult, Integer maxResult);
}
