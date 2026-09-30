package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ControlloDAO;
import it.gruppoinit.pal.gp.core.domain.Controllo;
import it.gruppoinit.pal.gp.core.domain.ControlloId;

import java.util.List;

/**
 * 
 * @author
 */
public interface ControlloService extends BaseService<Controllo, ControlloId> {

    /**
     * @see ControlloDAO#findAll(Integer, Integer)
     */
    public List<Controllo> findAll(Integer firstResult, Integer maxResult);
}
