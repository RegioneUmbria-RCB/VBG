package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipiprocedureDyn2modellitDAO;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDyn2modellitId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface TipiprocedureDyn2modellitService extends BaseService<TipiprocedureDyn2modellit, TipiprocedureDyn2modellitId> {

    /**
     * @see TipiprocedureDyn2modellitDAO#findAll(Integer, Integer)
     */
    public List<TipiprocedureDyn2modellit> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna i modelli dinamici (TipiprocedureDyn2modellit) associati alla procedura passata (Tipiprocedure). Il
     * risultato è ordinato per il campo ordine (Asc) e per il campo descrizione del modello associato (asc)
     * 
     * 
     * @param codicetipoprocedura
     * @return
     */
    public List<TipiprocedureDyn2modellit> findByTipoprocedura(Integer codicetipoprocedura);
}
