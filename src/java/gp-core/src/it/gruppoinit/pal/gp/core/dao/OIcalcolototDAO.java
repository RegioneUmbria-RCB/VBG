package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OIcalcolotot;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OIcalcolototDAO extends BaseDAO<OIcalcolotot, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OIcalcolotot> findAll(Integer firstResult, Integer maxResult);
}
