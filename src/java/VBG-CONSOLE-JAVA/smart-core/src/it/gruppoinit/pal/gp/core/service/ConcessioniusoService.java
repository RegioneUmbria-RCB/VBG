package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ConcessioniusoDAO;
import it.gruppoinit.pal.gp.core.domain.Concessioniuso;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 * @author Riccardo Bocci
 */
public interface ConcessioniusoService extends BaseService<Concessioniuso, PkId> {

    /**
     * @see ConcessioniusoDAO#findAll(Integer, Integer)
     */
    public List<Concessioniuso> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see ConcessioniusoDAO#findByFilter(Concessioniuso entity)
     */
    public List<Concessioniuso> findByFilter(Concessioniuso entity);
}
