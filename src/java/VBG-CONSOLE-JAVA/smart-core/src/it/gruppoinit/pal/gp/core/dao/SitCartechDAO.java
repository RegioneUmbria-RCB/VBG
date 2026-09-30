package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.SitCartech;

import java.util.List;

/**
 * 
 * @author 
 */
public interface SitCartechDAO extends BaseDAO<SitCartech, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<SitCartech> findAll(Integer firstResult, Integer maxResult);
}
