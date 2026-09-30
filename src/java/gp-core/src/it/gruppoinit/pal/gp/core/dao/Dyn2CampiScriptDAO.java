package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Dyn2CampiScript;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiScriptId;

import java.util.List;

/**
 * 
 * @author
 */
public interface Dyn2CampiScriptDAO extends BaseDAO<Dyn2CampiScript, Dyn2CampiScriptId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Dyn2CampiScript> findAll(Integer firstResult, Integer maxResult);
}
