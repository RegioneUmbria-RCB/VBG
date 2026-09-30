package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzelavoriDDAO;
import it.gruppoinit.pal.gp.core.domain.IstanzelavoriD;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface IstanzelavoriDService extends BaseService<IstanzelavoriD, PkId> {

    /**
     * @see IstanzelavoriDDAO#findAll(Integer, Integer)
     */
    public List<IstanzelavoriD> findAll(Integer firstResult, Integer maxResult);
}
