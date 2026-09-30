package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzepeopletDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzepeoplet;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

public interface IstanzepeopletService extends BaseService<Istanzepeoplet, PkId> {

    /**
     * Recupera istanza people a partire da un istanza se esiste
     */
    public List<Istanzepeoplet> findIstanzapeoletByIstanza(Istanze istanze);

    /**
     * @see IstanzepeopletDAO#findByFilterTable(FilterTable)
     */
    public List<Istanzepeoplet> findByFilterTable(FilterTable filterTable);
}
