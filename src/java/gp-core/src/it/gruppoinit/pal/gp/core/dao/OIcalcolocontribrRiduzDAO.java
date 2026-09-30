package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribrRiduz;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OIcalcolocontribrRiduzDAO extends BaseDAO<OIcalcolocontribrRiduz, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OIcalcolocontribrRiduz> findAll(Integer firstResult, Integer maxResult);
}
