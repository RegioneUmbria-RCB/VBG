package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcItabella4;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcItabella4DAO extends BaseDAO<CcItabella4, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcItabella4> findAll(Integer firstResult, Integer maxResult);
}
