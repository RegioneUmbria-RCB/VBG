package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcIcalcoloTcontributo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcIcalcoloTcontributoDAO extends BaseDAO<CcIcalcoloTcontributo, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcIcalcoloTcontributo> findAll(Integer firstResult, Integer maxResult);
}
