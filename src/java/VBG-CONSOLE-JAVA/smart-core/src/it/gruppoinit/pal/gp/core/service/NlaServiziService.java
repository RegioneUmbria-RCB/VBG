package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.NlaServiziDAO;
import it.gruppoinit.pal.gp.core.domain.NlaServizi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface NlaServiziService extends BaseService<NlaServizi, PkId> {

    /**
     * @see NlaServiziDAO#findAll(Integer, Integer)
     */
    public List<NlaServizi> findAll(Integer firstResult, Integer maxResult);
}
