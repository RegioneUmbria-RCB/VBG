package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipiaffissioniDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiaffissioni;

import java.util.List;

/**
 * 
 * @author 
 */
public interface TipiaffissioniService extends BaseService<Tipiaffissioni, PkId> {

    /**
     * @see TipiaffissioniDAO#findAll(Integer, Integer)
     */
    public List<Tipiaffissioni> findAll(Integer firstResult, Integer maxResult);
}
