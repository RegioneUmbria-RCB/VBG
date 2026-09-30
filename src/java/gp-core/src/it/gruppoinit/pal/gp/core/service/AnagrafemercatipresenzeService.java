package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AnagrafemercatipresenzeDAO;
import it.gruppoinit.pal.gp.core.domain.Anagrafemercatipresenze;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AnagrafemercatipresenzeService extends BaseService<Anagrafemercatipresenze, PkId> {

    /**
     * @see AnagrafemercatipresenzeDAO#findAll(Integer, Integer)
     */
    public List<Anagrafemercatipresenze> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle Anagrafemercatipresenze di un'anagrafe
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Anagrafemercatipresenze> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);
}
