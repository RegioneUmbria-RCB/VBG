package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CanoniTipisuperficiDAO;
import it.gruppoinit.pal.gp.core.domain.CanoniTipisuperfici;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CanoniTipisuperficiService extends BaseService<CanoniTipisuperfici, PkId> {

    /**
     * @see CanoniTipisuperficiDAO#findAll(Integer, Integer)
     */
    public List<CanoniTipisuperfici> findAll(Integer firstResult, Integer maxResult);
}
