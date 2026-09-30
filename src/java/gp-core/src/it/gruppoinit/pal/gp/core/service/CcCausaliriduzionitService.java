package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcCausaliriduzionitDAO;
import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionit;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcCausaliriduzionitService extends BaseService<CcCausaliriduzionit, PkId> {

    /**
     * @see CcCausaliriduzionitDAO#findAll(Integer, Integer)
     */
    public List<CcCausaliriduzionit> findAll(Integer firstResult, Integer maxResult);
}
