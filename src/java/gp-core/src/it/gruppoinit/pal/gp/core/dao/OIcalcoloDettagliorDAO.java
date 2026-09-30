package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OIcalcoloDettaglior;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OIcalcoloDettagliorDAO extends BaseDAO<OIcalcoloDettaglior, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OIcalcoloDettaglior> findAll(Integer firstResult, Integer maxResult);
}
