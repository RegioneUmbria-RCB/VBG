package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiproprietaId;

import java.util.List;

/**
 * 
 * @author
 */
public interface Dyn2CampiproprietaDAO extends BaseDAO<Dyn2Campiproprieta, Dyn2CampiproprietaId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Dyn2Campiproprieta> findAll(Integer firstResult, Integer maxResult);
}
