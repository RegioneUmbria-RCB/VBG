package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OIcalcoloDettagliot;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OIcalcoloDettagliotDAO extends BaseDAO<OIcalcoloDettagliot, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OIcalcoloDettagliot> findAll(Integer firstResult, Integer maxResult);
}
