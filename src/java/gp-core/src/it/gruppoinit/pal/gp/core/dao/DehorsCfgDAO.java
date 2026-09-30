package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.DehorsCfg;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface DehorsCfgDAO extends BaseDAO<DehorsCfg, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<DehorsCfg> findAll(Integer firstResult, Integer maxResult);
}
