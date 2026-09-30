package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoVisuraContestiCampiBaseDAO;
import it.gruppoinit.pal.gp.core.domain.FoVisuraContestiCampiBase;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoVisuraContestiCampiBaseService extends BaseService<FoVisuraContestiCampiBase, PkId> {

    /**
     * @see FoVisuraContestiCampiBaseDAO#findAll(Integer, Integer)
     */
    public List<FoVisuraContestiCampiBase> findAll(Integer firstResult, Integer maxResult);
}
