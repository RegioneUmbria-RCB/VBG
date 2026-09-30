package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Movimentihummingbird;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface MovimentihummingbirdDAO extends BaseDAO<Movimentihummingbird, PkId> {

    /**
     * throw new {@link NotImplementedException}
     * 
     */
    public List<Movimentihummingbird> findAll(Integer firstResult, Integer maxResult);
}
