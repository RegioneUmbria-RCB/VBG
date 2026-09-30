package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.BachecalavorocercaDAO;
import it.gruppoinit.pal.gp.core.domain.Bachecalavorocerca;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface BachecalavorocercaService extends BaseService<Bachecalavorocerca, PkId> {

    /**
     * @see BachecalavorocercaDAO#findAll(Integer, Integer)
     */
    public List<Bachecalavorocerca> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle Bachecalavorocerca di un'anagrafe
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Bachecalavorocerca> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);
}
