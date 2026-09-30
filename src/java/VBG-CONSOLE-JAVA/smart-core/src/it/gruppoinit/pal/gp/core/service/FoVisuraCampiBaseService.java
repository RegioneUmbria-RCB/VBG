package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoVisuraCampiBaseDAO;
import it.gruppoinit.pal.gp.core.domain.FoVisuraCampiBase;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoVisuraCampiBaseService extends BaseService<FoVisuraCampiBase, String> {

    /**
     * @see FoVisuraCampiBaseDAO#findAll(Integer, Integer)
     */
    public List<FoVisuraCampiBase> findAll(Integer firstResult, Integer maxResult);
}
