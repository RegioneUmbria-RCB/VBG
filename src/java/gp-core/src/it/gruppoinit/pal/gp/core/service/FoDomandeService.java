package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoDomandeDAO;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoDomandeService extends BaseService<FoDomande, PkId> {

    /**
     * @see FoDomandeDAO#findAll(Integer, Integer)
     */
    public List<FoDomande> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle FoDomande filtrando per idcomune e identificativodomanda
     * 
     * @param identificativodomanda
     * @return
     */
    public List<FoDomande> findByIdentificativodomanda(String identificativodomanda);

    /**
     * Torna la lista delle FoDomande di un'anagrafe
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<FoDomande> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    public List<FoDomande> findDomandeInSospeso(Integer firstResult, Integer maxResult);
}
