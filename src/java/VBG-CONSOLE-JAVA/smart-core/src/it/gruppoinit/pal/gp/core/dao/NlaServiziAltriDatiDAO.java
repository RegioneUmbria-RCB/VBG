package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.NlaServiziAltriDati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface NlaServiziAltriDatiDAO extends BaseDAO<NlaServiziAltriDati, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<NlaServiziAltriDati> findAll(Integer firstResult, Integer maxResult);
}
