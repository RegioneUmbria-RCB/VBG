package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OIcalcoloDettagliotDAO;
import it.gruppoinit.pal.gp.core.domain.OIcalcoloDettagliot;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OIcalcoloDettagliotService extends BaseService<OIcalcoloDettagliot, PkId> {

    /**
     * @see OIcalcoloDettagliotDAO#findAll(Integer, Integer)
     */
    public List<OIcalcoloDettagliot> findAll(Integer firstResult, Integer maxResult);
}
