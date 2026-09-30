package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OIcalcoloDettagliorDAO;
import it.gruppoinit.pal.gp.core.domain.OIcalcoloDettaglior;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OIcalcoloDettagliorService extends BaseService<OIcalcoloDettaglior, PkId> {

    /**
     * @see OIcalcoloDettagliorDAO#findAll(Integer, Integer)
     */
    public List<OIcalcoloDettaglior> findAll(Integer firstResult, Integer maxResult);
}
