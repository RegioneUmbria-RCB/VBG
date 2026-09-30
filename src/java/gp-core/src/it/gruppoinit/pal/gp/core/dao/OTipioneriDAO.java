package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OTipioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OTipioneriDAO extends BaseDAO<OTipioneri, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OTipioneri> findAll(Integer firstResult, Integer maxResult);
}
