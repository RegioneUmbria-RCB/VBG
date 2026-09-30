package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlboCategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface AlboCategorieDAO extends BaseDAO<AlboCategorie, PkId> {

    /**
     * Restituisce le Categorie dell'Albo (filtrando per idcomune e software) ordinandole per il campo ordine ascendente
     */
    public List<AlboCategorie> findAll(Integer firstResult, Integer maxResult);
}
