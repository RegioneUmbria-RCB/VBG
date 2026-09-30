package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiStoricoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IAttivitadyn2datiStoricoDAO extends BaseDAO<IAttivitadyn2datiStorico, IAttivitadyn2datiStoricoId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IAttivitadyn2datiStorico> findAll(Integer firstResult, Integer maxResult);
}
