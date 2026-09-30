package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoliDettagliotDAO;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoliDettagliot;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcIcalcoliDettagliotService extends BaseService<CcIcalcoliDettagliot, PkId> {

    /**
     * @see CcIcalcoliDettagliotDAO#findAll(Integer, Integer)
     */
    public List<CcIcalcoliDettagliot> findAll(Integer firstResult, Integer maxResult);
}
