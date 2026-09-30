package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcItabella2DAO;
import it.gruppoinit.pal.gp.core.domain.CcItabella2;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcItabella2Service extends BaseService<CcItabella2, PkId> {

    /**
     * @see CcItabella2DAO#findAll(Integer, Integer)
     */
    public List<CcItabella2> findAll(Integer firstResult, Integer maxResult);
}
