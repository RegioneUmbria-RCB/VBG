package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Dyn2CampiScriptDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiScript;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiScriptId;

import java.util.List;

/**
 * 
 * @author
 */
public interface Dyn2CampiScriptService extends BaseService<Dyn2CampiScript, Dyn2CampiScriptId> {

    /**
     * @see Dyn2CampiScriptDAO#findAll(Integer, Integer)
     */
    public List<Dyn2CampiScript> findAll(Integer firstResult, Integer maxResult);

    public Dyn2CampiScript findByCampoAndEvento(Integer codiceCampo, String evento);
}
