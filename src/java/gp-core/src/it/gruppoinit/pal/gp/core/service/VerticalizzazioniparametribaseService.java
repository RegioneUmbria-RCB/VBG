package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.VerticalizzazioniparametribaseDAO;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametribase;
import it.gruppoinit.pal.gp.core.domain.VerticalizzazioniparametribaseId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface VerticalizzazioniparametribaseService extends BaseService<Verticalizzazioniparametribase, VerticalizzazioniparametribaseId> {

    /**
     * @see VerticalizzazioniparametribaseDAO#findAll(Integer, Integer)
     */
    public List<Verticalizzazioniparametribase> findAll(Integer firstResult, Integer maxResult);

    public List<Verticalizzazioniparametribase> findByModulo(String modulo);
}
