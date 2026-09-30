package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IstanzelavoriD;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface IstanzelavoriDDAO extends BaseDAO<IstanzelavoriD, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IstanzelavoriD> findAll(Integer firstResult, Integer maxResult);
}
