package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Controllo;
import it.gruppoinit.pal.gp.core.domain.ControlloId;

import java.util.List;

/**
 * 
 * @author
 */
public interface ControlloDAO extends BaseDAO<Controllo, ControlloId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Controllo> findAll(Integer firstResult, Integer maxResult);
}
