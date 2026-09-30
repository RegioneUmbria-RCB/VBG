package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Categoriaaffissioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CategoriaaffissioniDAO extends BaseDAO<Categoriaaffissioni, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Categoriaaffissioni> findAll(Integer firstResult, Integer maxResult);
}
