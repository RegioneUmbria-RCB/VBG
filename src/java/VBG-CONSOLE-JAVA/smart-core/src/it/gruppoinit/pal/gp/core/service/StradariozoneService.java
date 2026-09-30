package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradariozone;

import java.util.List;

/**
 * @author Luca Proietti
 * @author gianpaolot
 * 
 */
public interface StradariozoneService extends BaseService<Stradariozone, PkId> {

    public List<Stradariozone> findByFilter(Stradariozone entity);

    /**
     * @see StradariozoenDAO#findAll(Integer, Integer)
     */
    public List<Stradariozone> findAll(Integer firstResult, Integer maxResult);
}
