package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.LavoricategorieDAO;
import it.gruppoinit.pal.gp.core.domain.Lavoricategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface LavoricategorieService extends BaseService<Lavoricategorie, PkId> {

    /**
     * @see LavoricategorieDAO#findAll(Integer, Integer)
     */
    public List<Lavoricategorie> findAll(Integer firstResult, Integer maxResult);

    public List<Lavoricategorie> findByFilterTable(FilterTable filterTable);
}
