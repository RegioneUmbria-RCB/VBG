package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiT;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeAccessoAttiTDAO extends BaseDAO<IstanzeAccessoAttiT, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IstanzeAccessoAttiT> findAll(Integer firstResult, Integer maxResult);
}
