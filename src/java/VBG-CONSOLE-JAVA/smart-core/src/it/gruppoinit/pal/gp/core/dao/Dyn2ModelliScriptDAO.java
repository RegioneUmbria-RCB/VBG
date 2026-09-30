package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Dyn2ModelliScript;
import it.gruppoinit.pal.gp.core.domain.Dyn2ModelliScriptId;

import java.util.List;

/**
 * 
 * @author
 */
public interface Dyn2ModelliScriptDAO extends BaseDAO<Dyn2ModelliScript, Dyn2ModelliScriptId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Dyn2ModelliScript> findAll(Integer firstResult, Integer maxResult);
}
