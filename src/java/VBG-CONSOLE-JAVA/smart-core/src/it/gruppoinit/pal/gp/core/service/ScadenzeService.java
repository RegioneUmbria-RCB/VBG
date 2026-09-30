package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ScadenzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ScadenzecategoriebaseEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Scadenze;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface ScadenzeService extends BaseService<Scadenze, PkId> {

    /**
     * @see ScadenzeDAO#findAll(Integer, Integer)
     */
    public List<Scadenze> findAll(Integer firstResult, Integer maxResult);

    /**
     * Trova le scadenze per l'anagrafica selezionata
     * 
     * @param anagrafe
     * @return
     */
    public List<Scadenze> findAvvisiForAnagrafe(Anagrafe anagrafe, boolean visualizzaSoloAttive);

    /**
     * Verifica se esistono scadenze/avvisi per l'anagrafe
     * 
     * @param anagrafe
     * @return
     */
    public boolean findExistsAvvisiForAnagrafe(Integer codiceAnagrafe, boolean visualizzaSoloAttive);

    /**
     * <pre>
     * Ritorna la lista di scadenze per un anagrafica filtrate per: 
     * 	
     * 	1- categoria (ALL mostra tutte le categorie)
     * 
     * @param codiceAnagrafe
     * @param scadenzecategoriebaseEnum
     * 
     * @return
     * </pre>
     */
    public List<Scadenze> findAvvisiForAnagrafe(Integer codiceAnagrafe, ScadenzecategoriebaseEnum scadenzecategoriebaseEnum);
}
