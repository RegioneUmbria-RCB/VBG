package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FDomandeDAO;
import it.gruppoinit.pal.gp.core.domain.FDomande;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FDomandeService extends BaseService<FDomande, PkId> {

    /**
     * @see FDomandeDAO#findAll(Integer, Integer)
     */
    public List<FDomande> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle FDomande di un'anagrafe
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<FDomande> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle FDomande di una societa
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<FDomande> findBySocieta(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);
    
    /**
     * Torna la lista delle FDomande di un subentro
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<FDomande> findBySubentro(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

}
