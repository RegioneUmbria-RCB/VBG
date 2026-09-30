package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribt;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OIcalcolocontribtDAO extends BaseDAO<OIcalcolocontribt, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OIcalcolocontribt> findAll(Integer firstResult, Integer maxResult);
}
