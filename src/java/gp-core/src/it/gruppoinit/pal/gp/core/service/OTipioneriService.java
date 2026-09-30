package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OTipioneriDAO;
import it.gruppoinit.pal.gp.core.domain.OTipioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OTipioneriService extends BaseService<OTipioneri, PkId> {

    /**
     * @see OTipioneriDAO#findAll(Integer, Integer)
     */
    public List<OTipioneri> findAll(Integer firstResult, Integer maxResult);
}
