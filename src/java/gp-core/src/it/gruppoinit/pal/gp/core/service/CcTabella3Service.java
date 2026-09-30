package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcTabella3DAO;
import it.gruppoinit.pal.gp.core.domain.CcTabella3;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcTabella3Service extends BaseService<CcTabella3, PkId> {

    /**
     * @see CcTabella3DAO#findAll(Integer, Integer)
     */
    public List<CcTabella3> findAll(Integer firstResult, Integer maxResult);
}
