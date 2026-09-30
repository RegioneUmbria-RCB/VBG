package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontributo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcIcalcoloDcontributoDAO extends BaseDAO<CcIcalcoloDcontributo, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcIcalcoloDcontributo> findAll(Integer firstResult, Integer maxResult);
}
