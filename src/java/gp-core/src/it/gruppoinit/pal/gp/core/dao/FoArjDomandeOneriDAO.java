package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoArjDomandeOneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface FoArjDomandeOneriDAO extends BaseDAO<FoArjDomandeOneri, PkId> {

    /**
     * 
     * 
     */
    public List<FoArjDomandeOneri> findAll(Integer firstResult, Integer maxResult);
}
