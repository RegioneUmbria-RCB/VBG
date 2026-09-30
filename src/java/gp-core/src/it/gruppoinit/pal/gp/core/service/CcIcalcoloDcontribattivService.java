package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoloDcontribattivDAO;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontribattiv;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcIcalcoloDcontribattivService extends BaseService<CcIcalcoloDcontribattiv, PkId> {

    /**
     * @see CcIcalcoloDcontribattivDAO#findAll(Integer, Integer)
     */
    public List<CcIcalcoloDcontribattiv> findAll(Integer firstResult, Integer maxResult);
}
