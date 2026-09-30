package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.SettoriavvisiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Settoriavvisi;

import java.util.List;

/**
 * 
 * @author gianpaolot
 * 
 */
public interface SettoriavvisiService extends BaseService<Settoriavvisi, PkId> {

    /**
     * @see SettoriavvisiDAO#findAll(Integer, Integer)
     */
    public List<Settoriavvisi> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see SettoriavvisiDAO#findByFilter(Settoriavvisi filter)
     */
    public List<Settoriavvisi> findByFilter(Settoriavvisi filter);
}
