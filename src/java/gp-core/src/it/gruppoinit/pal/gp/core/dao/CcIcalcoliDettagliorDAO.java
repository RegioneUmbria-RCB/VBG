package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcIcalcoliDettaglior;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcIcalcoliDettagliorDAO extends BaseDAO<CcIcalcoliDettaglior, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcIcalcoliDettaglior> findAll(Integer firstResult, Integer maxResult);
}
