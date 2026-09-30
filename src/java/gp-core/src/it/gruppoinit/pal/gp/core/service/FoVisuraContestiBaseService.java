package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoVisuraContestiBaseDAO;
import it.gruppoinit.pal.gp.core.domain.FoVisuraContestiBase;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoVisuraContestiBaseService extends BaseService<FoVisuraContestiBase, String> {

    /**
     * @see FoVisuraContestiBaseDAO#findAll(Integer, Integer)
     */
    public List<FoVisuraContestiBase> findAll(Integer firstResult, Integer maxResult);
}
