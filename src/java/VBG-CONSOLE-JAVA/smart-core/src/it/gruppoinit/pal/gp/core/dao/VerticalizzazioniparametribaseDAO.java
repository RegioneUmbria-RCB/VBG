package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametribase;
import it.gruppoinit.pal.gp.core.domain.VerticalizzazioniparametribaseId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface VerticalizzazioniparametribaseDAO extends BaseDAO<Verticalizzazioniparametribase, VerticalizzazioniparametribaseId> {

    /**
     * Lista di verticalizzazioni parametri base
     * 
     */
    public List<Verticalizzazioniparametribase> findAll(Integer firstResult, Integer maxResult);
}
