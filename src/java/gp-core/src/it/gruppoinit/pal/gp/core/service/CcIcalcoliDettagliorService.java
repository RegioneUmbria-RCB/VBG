package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoliDettagliorDAO;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoliDettaglior;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcIcalcoliDettagliorService extends BaseService<CcIcalcoliDettaglior, PkId> {

    /**
     * @see CcIcalcoliDettagliorDAO#findAll(Integer, Integer)
     */
    public List<CcIcalcoliDettaglior> findAll(Integer firstResult, Integer maxResult);
}
