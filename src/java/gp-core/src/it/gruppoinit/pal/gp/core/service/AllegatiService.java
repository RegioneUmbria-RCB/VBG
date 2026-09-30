package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

public interface AllegatiService extends BaseService<Allegati, PkId> {

    public List<Allegati> findByFilterTable(FilterTable filterTable);

    public List<Allegati> findByInventarioprocedimenti(Integer codiceInventario);

    public int countByInventarioprocedimenti(Integer codiceInventario);

    /**
     * Torna la lista delle Allegati di un'Amministrazione
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Allegati> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);
}
