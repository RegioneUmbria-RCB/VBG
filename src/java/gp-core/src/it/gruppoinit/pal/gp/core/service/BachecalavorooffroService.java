package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.BachecalavorooffroDAO;
import it.gruppoinit.pal.gp.core.domain.Bachecalavorooffro;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface BachecalavorooffroService extends BaseService<Bachecalavorooffro, PkId> {

    /**
     * @see BachecalavorooffroDAO#findAll(Integer, Integer)
     */
    public List<Bachecalavorooffro> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle Bachecalavorooffro di un'anagrafe
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Bachecalavorooffro> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);
}
