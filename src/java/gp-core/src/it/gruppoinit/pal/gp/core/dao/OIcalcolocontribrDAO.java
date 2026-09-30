package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribr;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OIcalcolocontribrDAO extends BaseDAO<OIcalcolocontribr, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OIcalcolocontribr> findAll(Integer firstResult, Integer maxResult);
}
