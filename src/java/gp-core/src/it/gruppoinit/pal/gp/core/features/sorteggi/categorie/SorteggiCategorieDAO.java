package it.gruppoinit.pal.gp.core.features.sorteggi.categorie;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.SorteggiCategorie;

/**
 * 
 * @author fabrizioc
 * 
 */
public interface SorteggiCategorieDAO extends BaseDAO<SorteggiCategorie, PkId> {

    /**
     * Restituisce la lista delle categorie sorteggi filtrate per Idcomune e software e ordinata per descrizione asc
     * 
     */
    public List<SorteggiCategorie> findAll(Integer firstResult, Integer maxResult);

    public List<SorteggiCategorie> findBySoftware(String software);
}
