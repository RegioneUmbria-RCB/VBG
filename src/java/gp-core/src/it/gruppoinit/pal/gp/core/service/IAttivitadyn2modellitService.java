package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2modellitDAO;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellit;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellitId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IAttivitadyn2modellitService extends BaseService<IAttivitadyn2modellit, IAttivitadyn2modellitId> {

    /**
     * @see IAttivitadyn2modellitDAO#findAll(Integer, Integer)
     */
    public List<IAttivitadyn2modellit> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di modelli dell'attività filtrati per attivita e paginati
     * 
     * @param iAttivita
     *            : attivita a cui sono associati
     * @param firstResult
     *            : primo risultato cercato
     * @param maxResult
     *            : numero massimo di risultati cercati
     * @return
     */
    public List<IAttivitadyn2modellit> findByAttivita(Integer iAttivita, Integer firstResult, Integer maxResult);

    /**
     * Ritorna il numero di modelli dell'attività filtrati per attivita e paginati
     * 
     * @param iAttivita
     *            : attivita a cui sono associati
     * 
     * @return
     */
    public int countByAttivita(Integer iAttivita);

    /**
     * Recupera IAttivitadyn2modellit fitrando per il codice del modello
     * 
     * @param codiceModello
     * @return
     */
    public IAttivitadyn2modellit findByAttivitaAndModello(Integer codiceAttivita, Integer codiceModello);

    /**
     * Verifica se per in codice attività passato esiste la scheda con "codice_scheda" passato
     * 
     * @param codiceAttivita
     * @param codiceScheda
     * @return
     */
    public boolean existsRecordsByCodiceScheda(Integer codiceAttivita, String codiceScheda);
}
