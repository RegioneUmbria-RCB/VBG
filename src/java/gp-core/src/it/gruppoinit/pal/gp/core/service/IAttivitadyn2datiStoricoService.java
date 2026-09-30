package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2datiStoricoDAO;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiStoricoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IAttivitadyn2datiStoricoService extends BaseService<IAttivitadyn2datiStorico, IAttivitadyn2datiStoricoId> {

    /**
     * @see IAttivitadyn2datiStoricoDAO#findAll(Integer, Integer)
     */
    public List<IAttivitadyn2datiStorico> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna la lista dei dati associati alle schede storiche di una di un attività
     * 
     * @param fkIaId
     * @return
     */
    public List<IAttivitadyn2datiStorico> findByAttivita(Integer codiceAttivita);
}
