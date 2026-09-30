package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.NlaServiziAltriDatiDAO;
import it.gruppoinit.pal.gp.core.domain.NlaServiziAltriDati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface NlaServiziAltriDatiService extends BaseService<NlaServiziAltriDati, PkId> {

    /**
     * @see NlaServiziAltriDatiDAO#findAll(Integer, Integer)
     */
    public List<NlaServiziAltriDati> findAll(Integer firstResult, Integer maxResult);

    public List<NlaServiziAltriDati> findByNlaServizi(Integer codiceservizio);
}
