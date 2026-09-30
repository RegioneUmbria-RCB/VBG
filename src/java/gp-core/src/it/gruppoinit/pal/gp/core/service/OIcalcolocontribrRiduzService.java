package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OIcalcolocontribrRiduzDAO;
import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribrRiduz;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OIcalcolocontribrRiduzService extends BaseService<OIcalcolocontribrRiduz, PkId> {

    /**
     * @see OIcalcolocontribrRiduzDAO#findAll(Integer, Integer)
     */
    public List<OIcalcolocontribrRiduz> findAll(Integer firstResult, Integer maxResult);
}
