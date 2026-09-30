package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcClassisuperficiDAO;
import it.gruppoinit.pal.gp.core.domain.CcClassisuperfici;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcClassisuperficiService extends BaseService<CcClassisuperfici, PkId> {

    /**
     * @see CcClassisuperficiDAO#findAll(Integer, Integer)
     */
    public List<CcClassisuperfici> findAll(Integer firstResult, Integer maxResult);
}
