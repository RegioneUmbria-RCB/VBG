package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwScadenzario;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author
 */
public interface VwScadenzarioService extends BaseService<VwScadenzario, PkId> {

    public List<VwScadenzario> findByFilterTable(FilterTable ft);
}
