package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellit;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellitId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface IAttivitadyn2modellitDAO extends BaseDAO<IAttivitadyn2modellit, IAttivitadyn2modellitId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IAttivitadyn2modellit> findAll(Integer firstResult, Integer maxResult);
}
