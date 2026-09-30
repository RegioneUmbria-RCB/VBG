package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcTipisuperficieDAO;
import it.gruppoinit.pal.gp.core.domain.CcTipisuperficie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcTipisuperficieService extends BaseService<CcTipisuperficie, PkId> {

    /**
     * @see CcTipisuperficieDAO#findAll(Integer, Integer)
     */
    public List<CcTipisuperficie> findAll(Integer firstResult, Integer maxResult);
}
