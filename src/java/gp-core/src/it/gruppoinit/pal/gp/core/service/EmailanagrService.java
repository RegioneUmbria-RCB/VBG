package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.EmailanagrDAO;
import it.gruppoinit.pal.gp.core.domain.Emailanagr;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface EmailanagrService extends BaseService<Emailanagr, PkId> {

    /**
     * @see EmailanagrDAO#findAll(Integer, Integer)
     */
    public List<Emailanagr> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle Emailanagr di un'anagrafe
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Emailanagr> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);
}
