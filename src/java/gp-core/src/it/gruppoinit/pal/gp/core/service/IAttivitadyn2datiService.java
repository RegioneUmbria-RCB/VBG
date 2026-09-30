package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2datiDAO;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2dati;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiId;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitadyn2datiFilter;

import java.util.List;

/**
 * 
 * @author
 */
public interface IAttivitadyn2datiService extends BaseService<IAttivitadyn2dati, IAttivitadyn2datiId> {

    /**
     * @see IAttivitadyn2datiDAO#findAll(Integer, Integer)
     */
    public List<IAttivitadyn2dati> findAll(Integer firstResult, Integer maxResult);

    /**
     * Recupera tutti i valori associtati a Dyn2Campi per l'attività passata
     * 
     * @param iAttivita
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<IAttivitadyn2dati> findByAttivita(IAttivita iAttivita, Integer firstResult, Integer maxResult);

    @Override
    public void delete(IAttivitadyn2dati entity);

    /**
     * <pre>
     * Ritorna una lista, se esite almeno un record, di IAttivitadyn2dati utilizzando i possibili filri per:
     *    
     *    String  idcoume;
     *    Integer fkIaId;
     *    Integer fkD2cId;
     *    Integer indice;
     *    Integer indiceMolteplicita;
     * @param filter: classe che contiene i filtri sopra indicati
     * @return un record di IAttivitadyn2dati o null se non esiste.
     * </pre>
     */
    public List<IAttivitadyn2dati> findByFilter(IAttivitadyn2datiFilter filter);

    /**
     * Torna la lista dei dati dinamici individuati per una attivita individuati dal codiceCampo ordinati per
     * indicempolteplicita desc
     * 
     * @param codiceIstanza
     * @param codiceCampo
     * @param indice
     * @return
     */
    public List<IAttivitadyn2dati> findByAttivitaAndDyn2Campi(Integer codiceattivita, Integer codice, Integer indice);
}
