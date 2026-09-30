package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniD;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface IstanzecalcolocanoniDDAO extends BaseDAO<IstanzecalcolocanoniD, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IstanzecalcolocanoniD> findAll(Integer firstResult, Integer maxResult);
}
