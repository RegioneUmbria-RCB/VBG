package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcItabella3DAO;
import it.gruppoinit.pal.gp.core.domain.CcItabella3;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcItabella3Service extends BaseService<CcItabella3, PkId> {

    /**
     * @see CcItabella3DAO#findAll(Integer, Integer)
     */
    public List<CcItabella3> findAll(Integer firstResult, Integer maxResult);
}
