package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipidocumentoDAO;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipidocumento;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author francescop
 */
public interface TipidocumentoService extends BaseService<Tipidocumento, PkId> {

    /**
     * @see TipidocumentoDAO#findByFilterTable(FilterTable filterTable)
     */
    public List<Tipidocumento> findByFilterTable(FilterTable filterTable);

    /**
     * Trova tutti i record legati alla lettera tipo passata come argomento
     * 
     * @param letteretipo
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Tipidocumento> findByLetteretipo(Letteretipo letteretipo, int firstResult, int maxResult);
}
