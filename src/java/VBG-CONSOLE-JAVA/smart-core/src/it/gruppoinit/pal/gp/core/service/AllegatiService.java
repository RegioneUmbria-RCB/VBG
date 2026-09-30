package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;
import java.util.Set;

public interface AllegatiService extends BaseService<Allegati, PkId> {

    public List<Allegati> findByFilterTable(FilterTable filterTable);

    public List<Allegati> findByInventarioprocedimenti(String idcomune, Integer codiceInventario);

    public int countByInventarioprocedimenti(String idcomune, Integer codiceInventario);

    /**
     * Torna la lista delle Allegati di un'Amministrazione
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Allegati> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);

    public List<Allegati> findByInventarioprocedimentoAndComune(Integer codiceInventario, String idcomunecodiceinventario, String idcomunerecord);
}
