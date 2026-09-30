package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Bachecalavorocerca;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface BachecalavorocercaDAO extends BaseDAO<Bachecalavorocerca, PkId> {

    /**
     * 
     * @return Una lista di richieste di lavoro ordinate per data di scadenza desc
     */
    public List<Bachecalavorocerca> findAll(Integer firstResult, Integer maxResult);
}
