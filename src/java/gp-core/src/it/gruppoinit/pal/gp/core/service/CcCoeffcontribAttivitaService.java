package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcCoeffcontribAttivitaDAO;
import it.gruppoinit.pal.gp.core.domain.CcCoeffcontribAttivita;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcCoeffcontribAttivitaService extends BaseService<CcCoeffcontribAttivita, PkId> {

    /**
     * @see CcCoeffcontribAttivitaDAO#findAll(Integer, Integer)
     */
    public List<CcCoeffcontribAttivita> findAll(Integer firstResult, Integer maxResult);
}
