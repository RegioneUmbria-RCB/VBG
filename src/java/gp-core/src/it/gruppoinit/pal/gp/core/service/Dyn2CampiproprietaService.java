package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Dyn2CampiproprietaDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiproprietaId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface Dyn2CampiproprietaService extends BaseService<Dyn2Campiproprieta, Dyn2CampiproprietaId> {

    /**
     * @see Dyn2CampiproprietaDAO#findAll(Integer, Integer)
     */
    public List<Dyn2Campiproprieta> findAll(Integer firstResult, Integer maxResult);

    public List<Dyn2Campiproprieta> findByDyn2Campi(Integer codice);
}
