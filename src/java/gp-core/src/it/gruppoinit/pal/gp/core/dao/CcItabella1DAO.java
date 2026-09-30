package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcItabella1;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcItabella1DAO extends BaseDAO<CcItabella1, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcItabella1> findAll(Integer firstResult, Integer maxResult);
}
