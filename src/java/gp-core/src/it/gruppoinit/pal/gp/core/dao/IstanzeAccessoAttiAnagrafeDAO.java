package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiAnagrafe;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiAnagrafeId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeAccessoAttiAnagrafeDAO extends BaseDAO<IstanzeAccessoAttiAnagrafe, IstanzeAccessoAttiAnagrafeId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IstanzeAccessoAttiAnagrafe> findAll(Integer firstResult, Integer maxResult);
}
