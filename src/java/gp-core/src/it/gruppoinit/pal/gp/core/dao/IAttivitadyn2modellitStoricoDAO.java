package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellitStorico;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellitStoricoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IAttivitadyn2modellitStoricoDAO extends BaseDAO<IAttivitadyn2modellitStorico, IAttivitadyn2modellitStoricoId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IAttivitadyn2modellitStorico> findAll(Integer firstResult, Integer maxResult);
}
