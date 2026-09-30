package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.PertinenzeCoefficientiDAO;
import it.gruppoinit.pal.gp.core.domain.PertinenzeCoefficienti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface PertinenzeCoefficientiService extends BaseService<PertinenzeCoefficienti, PkId> {

    /**
     * @see PertinenzeCoefficientiDAO#findAll(Integer, Integer)
     */
    public List<PertinenzeCoefficienti> findAll(Integer firstResult, Integer maxResult);
}
