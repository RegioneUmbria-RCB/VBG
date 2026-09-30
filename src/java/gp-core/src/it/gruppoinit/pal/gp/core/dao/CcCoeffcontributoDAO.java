package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcCoeffcontributo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcCoeffcontributoDAO extends BaseDAO<CcCoeffcontributo, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcCoeffcontributo> findAll(Integer firstResult, Integer maxResult);
}
