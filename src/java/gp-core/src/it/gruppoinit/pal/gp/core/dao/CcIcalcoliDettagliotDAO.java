package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcIcalcoliDettagliot;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcIcalcoliDettagliotDAO extends BaseDAO<CcIcalcoliDettagliot, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcIcalcoliDettagliot> findAll(Integer firstResult, Integer maxResult);
}
