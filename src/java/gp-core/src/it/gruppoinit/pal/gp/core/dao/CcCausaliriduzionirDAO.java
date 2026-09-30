package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionir;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcCausaliriduzionirDAO extends BaseDAO<CcCausaliriduzionir, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcCausaliriduzionir> findAll(Integer firstResult, Integer maxResult);
}
