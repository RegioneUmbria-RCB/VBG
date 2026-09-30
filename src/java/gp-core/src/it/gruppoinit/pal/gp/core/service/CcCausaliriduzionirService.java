package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcCausaliriduzionirDAO;
import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionir;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcCausaliriduzionirService extends BaseService<CcCausaliriduzionir, PkId> {

    /**
     * @see CcCausaliriduzionirDAO#findAll(Integer, Integer)
     */
    public List<CcCausaliriduzionir> findAll(Integer firstResult, Integer maxResult);
}
