package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PertinenzeCoefficienti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface PertinenzeCoefficientiDAO extends BaseDAO<PertinenzeCoefficienti, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<PertinenzeCoefficienti> findAll(Integer firstResult, Integer maxResult);
}
