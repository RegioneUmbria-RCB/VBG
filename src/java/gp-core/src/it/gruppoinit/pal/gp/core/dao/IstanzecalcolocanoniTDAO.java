package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniT;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface IstanzecalcolocanoniTDAO extends BaseDAO<IstanzecalcolocanoniT, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IstanzecalcolocanoniT> findAll(Integer firstResult, Integer maxResult);
}
