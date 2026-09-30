package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcItabella1DAO;
import it.gruppoinit.pal.gp.core.domain.CcItabella1;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcItabella1Service extends BaseService<CcItabella1, PkId> {

    /**
     * @see CcItabella1DAO#findAll(Integer, Integer)
     */
    public List<CcItabella1> findAll(Integer firstResult, Integer maxResult);
}
