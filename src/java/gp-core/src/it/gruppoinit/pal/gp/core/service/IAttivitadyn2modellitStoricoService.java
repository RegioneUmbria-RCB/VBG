package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2modellitStoricoDAO;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellitStorico;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellitStoricoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IAttivitadyn2modellitStoricoService extends BaseService<IAttivitadyn2modellitStorico, IAttivitadyn2modellitStoricoId> {

    /**
     * @see IAttivitadyn2modellitStoricoDAO#findAll(Integer, Integer)
     */
    public List<IAttivitadyn2modellitStorico> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna lista dei record della storicizzazione dei modelli dell'attivita (IAttivitadyn2modellitStorico) filtrati
     * per attività
     * 
     * @param codice
     * @return
     */
    public List<IAttivitadyn2modellitStorico> findByAttivita(Integer codice);
}
