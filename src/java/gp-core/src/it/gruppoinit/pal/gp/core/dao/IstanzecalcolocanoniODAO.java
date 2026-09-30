package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniO;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniOId;

/**
 * 
 * @author
 */
public interface IstanzecalcolocanoniODAO extends BaseDAO<IstanzecalcolocanoniO, IstanzecalcolocanoniOId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IstanzecalcolocanoniO> findAll(Integer firstResult, Integer maxResult);

    public void deleteByIdOnere(int istanzeOneriId);
}
