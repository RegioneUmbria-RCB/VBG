package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Istanzedyn2modellitDAO;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellitId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

public interface Inventarioprocdyn2modellitService extends BaseService<Inventarioprocdyn2modellit, Inventarioprocdyn2modellitId> {

    /**
     * @see Istanzedyn2modellitDAO#findByFilterTable(FilterTable)
     */
    public List<Inventarioprocdyn2modellit> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult);

    /**
     * Ritorna i modelli dinamici (Inventarioprocdyn2modellit) associati all'endoprocedimento passato (
     * Inventarioprocedimenti). Il risultato è ordinato per il campo ordine (Asc) e per il campo descrizione del modello
     * associato (asc)
     * 
     * @param codiceendo
     * @return
     */
    public List<Inventarioprocdyn2modellit> findByInventarioprocedimento(Integer codiceendo);
}
