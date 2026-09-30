package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiD;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeAccessoAttiDDAO extends BaseDAO<IstanzeAccessoAttiD, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IstanzeAccessoAttiD> findAll(Integer firstResult, Integer maxResult);
}
