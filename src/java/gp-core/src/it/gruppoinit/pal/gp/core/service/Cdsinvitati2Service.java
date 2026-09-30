package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Cdsinvitati2DAO;
import it.gruppoinit.pal.gp.core.domain.Cds;
import it.gruppoinit.pal.gp.core.domain.Cdsinvitati2;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author
 */
public interface Cdsinvitati2Service extends BaseService<Cdsinvitati2, PkId> {

    /**
     * @see Cdsinvitati2DAO#findAll(Integer, Integer)
     */
    public List<Cdsinvitati2> findAll(Integer firstResult, Integer maxResult);

    public List<Cdsinvitati2> findByFilterTable(FilterTable filterTable);

    /**
     * Ritorna una lista di cds invitati 2 filtrati per la cds passata e ordinati per la nominativo del responsabile
     * 
     * @param cds
     * @return
     */
    public List<Cdsinvitati2> findByCds(Cds cds);

    /**
     * Torna la lista delle Cdsinvitati2 di un'anagrafe
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Cdsinvitati2> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);
}
