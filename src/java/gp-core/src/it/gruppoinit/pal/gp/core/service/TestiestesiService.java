package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TestiestesiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Testiestesi;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface TestiestesiService extends BaseService<Testiestesi, PkId> {

    /**
     * @see TestiestesiDAO#findAll(Integer, Integer)
     */
    public List<Testiestesi> findAll(Integer firstResult, Integer maxResult);
}
