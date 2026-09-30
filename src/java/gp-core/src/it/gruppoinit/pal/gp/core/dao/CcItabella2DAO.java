package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcItabella2;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcItabella2DAO extends BaseDAO<CcItabella2, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcItabella2> findAll(Integer firstResult, Integer maxResult);
}
