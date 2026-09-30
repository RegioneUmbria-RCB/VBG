package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OIcalcolocontribrDAO;
import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribr;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OIcalcolocontribrService extends BaseService<OIcalcolocontribr, PkId> {

    /**
     * @see OIcalcolocontribrDAO#findAll(Integer, Integer)
     */
    public List<OIcalcolocontribr> findAll(Integer firstResult, Integer maxResult);
}
