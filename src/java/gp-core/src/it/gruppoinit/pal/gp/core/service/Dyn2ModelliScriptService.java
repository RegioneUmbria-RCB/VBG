package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Dyn2ModelliScriptDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2ModelliScript;
import it.gruppoinit.pal.gp.core.domain.Dyn2ModelliScriptId;

import java.util.List;

/**
 * 
 * @author
 */
public interface Dyn2ModelliScriptService extends BaseService<Dyn2ModelliScript, Dyn2ModelliScriptId> {

    /**
     * @see Dyn2ModelliScriptDAO#findAll(Integer, Integer)
     */
    public List<Dyn2ModelliScript> findAll(Integer firstResult, Integer maxResult);

    public Dyn2ModelliScript findByModelloAndEvento(Integer codiceModello, String name);
    
    
    public List<Dyn2ModelliScript> findByModello(Integer codiceModello) ;
}
