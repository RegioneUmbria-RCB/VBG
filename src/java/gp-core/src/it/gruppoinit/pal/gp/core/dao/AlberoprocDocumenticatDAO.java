package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenticat;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * @author lucap
 * 
 */
public interface AlberoprocDocumenticatDAO extends BaseDAO<AlberoprocDocumenticat, PkId> {

    /**
     * Restituisce tutte le Categorie di Documenti presenti sull'Albero dei Procedimenti (filtrando per idcomune e
     * software) e ordinandole per descrizione ascendente
     */
    public List<AlberoprocDocumenticat> findAll(Integer firstResult, Integer maxResult);
}
