package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoArjServiziDAO;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.FoArjServizi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface FoArjServiziService extends BaseService<FoArjServizi, PkId> {

    /**
     * @see FoArjServiziDAO#findAll(Integer, Integer)
     */
    public List<FoArjServizi> findAll(Integer firstResult, Integer maxResult);

    public List<FoArjServizi> findBySoftware(Integer firstResult, Integer maxResult);

    public List<FoArjServizi> findByAlberoproc(Integer codiceIntervento);

    public List<FoArjServizi> findByUrlServizio(String urlServizio);
}
