package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OIcalcolocontribtBtoDAO;
import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribtBto;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OIcalcolocontribtBtoService extends BaseService<OIcalcolocontribtBto, PkId> {

    /**
     * @see OIcalcolocontribtBtoDAO#findAll(Integer, Integer)
     */
    public List<OIcalcolocontribtBto> findAll(Integer firstResult, Integer maxResult);
}
