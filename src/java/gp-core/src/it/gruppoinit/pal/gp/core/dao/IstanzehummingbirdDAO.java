package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanzehummingbird;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface IstanzehummingbirdDAO extends BaseDAO<Istanzehummingbird, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Istanzehummingbird> findAll(Integer firstResult, Integer maxResult);
}
