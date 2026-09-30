package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribtBto;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OIcalcolocontribtBtoDAO extends BaseDAO<OIcalcolocontribtBto, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OIcalcolocontribtBto> findAll(Integer firstResult, Integer maxResult);
}
