package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.MovimentiContromovimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface MovimentiContromovimentiDAO extends BaseDAO<MovimentiContromovimenti, PkId> {

    /**
     * non implementato
     * 
     * @throws new
     *             {@link NotImplementedException}
     * 
     */
    public List<MovimentiContromovimenti> findAll(Integer firstResult, Integer maxResult);
}
