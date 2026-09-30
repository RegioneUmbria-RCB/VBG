package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcItabella3;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcItabella3DAO extends BaseDAO<CcItabella3, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcItabella3> findAll(Integer firstResult, Integer maxResult);
}
