package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Stradariocolore;
import it.gruppoinit.pal.gp.core.domain.StradariocoloreId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface StradariocoloreDAO extends BaseDAO<Stradariocolore, StradariocoloreId> {

    /**
     * Restituisce una lista di stradario colore (filtrando per idcomune ) ordinandole per il campo colore
     */
    public List<Stradariocolore> findAll(Integer firstResult, Integer maxResult);
}
