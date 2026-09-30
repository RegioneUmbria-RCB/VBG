package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanzedelete;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface IstanzedeleteDAO extends BaseDAO<Istanzedelete, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Istanzedelete> findAll(Integer firstResult, Integer maxResult);
}
