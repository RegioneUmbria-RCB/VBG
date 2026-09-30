package it.gruppoinit.pal.gp.core.features.sorteggi.categorie;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.SorteggiCategorie;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.BaseService;

/**
 * 
 * @author fabrizioc
 * 
 */
public interface SorteggiCategorieService extends BaseService<SorteggiCategorie, PkId> {

    /**
     * @see SorteggiCategorieDAO#findAll(Integer, Integer)
     */
    public List<SorteggiCategorie> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see BaseDAO#findByFilterTable(FilterTable)
     */
    public List<SorteggiCategorie> findByFilterTable(FilterTable filterTable);

    public List<SorteggiCategorie> findBySoftware(String software);
}
