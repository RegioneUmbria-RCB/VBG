package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoloTcontributoDAO;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloTcontributo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcIcalcoloTcontributoService extends BaseService<CcIcalcoloTcontributo, PkId> {

    /**
     * @see CcIcalcoloTcontributoDAO#findAll(Integer, Integer)
     */
    public List<CcIcalcoloTcontributo> findAll(Integer firstResult, Integer maxResult);
}
