package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcTabella3;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcTabella3DAO extends BaseDAO<CcTabella3, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcTabella3> findAll(Integer firstResult, Integer maxResult);
}
