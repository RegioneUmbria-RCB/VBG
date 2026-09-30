package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CdsinvitatiDAO;
import it.gruppoinit.pal.gp.core.domain.Cds;
import it.gruppoinit.pal.gp.core.domain.Cdsinvitati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author
 */
public interface CdsinvitatiService extends BaseService<Cdsinvitati, PkId> {

    /**
     * @see CdsinvitatiDAO#findAll(Integer, Integer)
     */
    public List<Cdsinvitati> findAll(Integer firstResult, Integer maxResult);

    public List<Cdsinvitati> findByFilterTable(FilterTable filterTable);

    /**
     * Ritorna una lista di cds invitati filtrati per la cds passata e ordinati per la descrizione dell'amministrazione
     * 
     * @param cds
     * @return
     */
    public List<Cdsinvitati> findByCds(Cds cds);

    /**
     * Torna la lista delle Cdsinvitati di un'Amministrazione
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Cdsinvitati> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);
}
