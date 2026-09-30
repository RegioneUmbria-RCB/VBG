package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IstanzelavoriT;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface IstanzelavoriTDAO extends BaseDAO<IstanzelavoriT, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IstanzelavoriT> findAll(Integer firstResult, Integer maxResult);
}
