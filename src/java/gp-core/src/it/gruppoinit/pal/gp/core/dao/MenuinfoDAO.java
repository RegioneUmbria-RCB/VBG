package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Menuinfo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface MenuinfoDAO extends BaseDAO<Menuinfo, PkId> {

    /**
     * Lista di menu info filtrati per idcomune
     * 
     */
    public List<Menuinfo> findAll(Integer firstResult, Integer maxResult);

    /**
     * Recupera l'ordine massimo della lista completa dei menù info
     * 
     * @return
     */
    public Integer findOrdineMax();
}
