package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontribattiv;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcIcalcoloDcontribattivDAO extends BaseDAO<CcIcalcoloDcontribattiv, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcIcalcoloDcontribattiv> findAll(Integer firstResult, Integer maxResult);
}
