package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CategoriaaffissioniDAO;
import it.gruppoinit.pal.gp.core.domain.Categoriaaffissioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CategoriaaffissioniService extends BaseService<Categoriaaffissioni, PkId> {

    /**
     * @see CategoriaaffissioniDAO#findAll(Integer, Integer)
     */
    public List<Categoriaaffissioni> findAll(Integer firstResult, Integer maxResult);
}
