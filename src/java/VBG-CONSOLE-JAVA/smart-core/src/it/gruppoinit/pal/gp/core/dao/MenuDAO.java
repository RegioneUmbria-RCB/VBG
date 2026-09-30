package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Menu;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface MenuDAO extends BaseDAO<Menu, PkId> {

    /**
     * Lista di menu filtrati per id comune
     * 
     */
    public List<Menu> findAll(Integer firstResult, Integer maxResult);

    /**
     * Recupera l'ordine massimo della lista completa dei menù
     * 
     * @return
     */
    public Integer findOrdineMax();
}
