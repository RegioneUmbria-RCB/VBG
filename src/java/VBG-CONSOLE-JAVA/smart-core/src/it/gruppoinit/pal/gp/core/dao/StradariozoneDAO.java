package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradariozone;

import java.util.List;

/**
 * @author Luca Proietti
 * @author gianpaolot
 * 
 */
public interface StradariozoneDAO extends BaseDAO<Stradariozone, PkId> {

    List<Stradariozone> findByFilter(Stradariozone entity);

    /**
     * Restituisce una lista di stradario zone (filtrando per idcomune e software) ordinandole per il campo zona
     */
    public List<Stradariozone> findAll(Integer firstResult, Integer maxResult);
}
