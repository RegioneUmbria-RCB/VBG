package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OIcalcolocontribtDAO;
import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribt;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OIcalcolocontribtService extends BaseService<OIcalcolocontribt, PkId> {

    /**
     * @see OIcalcolocontribtDAO#findAll(Integer, Integer)
     */
    public List<OIcalcolocontribt> findAll(Integer firstResult, Integer maxResult);
}
