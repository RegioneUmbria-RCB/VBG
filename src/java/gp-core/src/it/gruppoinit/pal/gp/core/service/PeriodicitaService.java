/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.PeriodicitaDAO;
import it.gruppoinit.pal.gp.core.domain.Periodicita;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface PeriodicitaService extends BaseService<Periodicita, Integer> {

    /**
     * @see PeriodicitaDAO#findAll(Integer, Integer)
     */
    public List<Periodicita> findAll(Integer firstResult, Integer maxResult);
}
