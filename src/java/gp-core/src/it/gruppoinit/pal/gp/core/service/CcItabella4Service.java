package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcItabella4DAO;
import it.gruppoinit.pal.gp.core.domain.CcItabella4;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcItabella4Service extends BaseService<CcItabella4, PkId> {

    /**
     * @see CcItabella4DAO#findAll(Integer, Integer)
     */
    public List<CcItabella4> findAll(Integer firstResult, Integer maxResult);
}
