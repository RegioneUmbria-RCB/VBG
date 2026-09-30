package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Testiestesi;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface TestiestesiDAO extends BaseDAO<Testiestesi, PkId> {

    /**
     * Rirtorna una lista di testi estesi filtrati per idcomune
     * 
     */
    public List<Testiestesi> findAll(Integer firstResult, Integer maxResult);
}
