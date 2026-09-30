package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CanoniConfigareeDAO;
import it.gruppoinit.pal.gp.core.domain.CanoniConfigaree;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CanoniConfigareeService extends BaseService<CanoniConfigaree, PkId> {

    /**
     * @see CanoniConfigareeDAO#findAll(Integer, Integer)
     */
    public List<CanoniConfigaree> findAll(Integer firstResult, Integer maxResult);
}
