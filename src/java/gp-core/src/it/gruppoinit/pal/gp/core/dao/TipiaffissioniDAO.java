package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiaffissioni;

import java.util.List;

/**
 * 
 * @author 
 */
public interface TipiaffissioniDAO extends BaseDAO<Tipiaffissioni, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Tipiaffissioni> findAll(Integer firstResult, Integer maxResult);
}
