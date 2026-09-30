package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcCoeffcontribAttivita;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcCoeffcontribAttivitaDAO extends BaseDAO<CcCoeffcontribAttivita, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcCoeffcontribAttivita> findAll(Integer firstResult, Integer maxResult);
}
