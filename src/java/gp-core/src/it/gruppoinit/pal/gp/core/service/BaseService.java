package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Interfaccia che contiene le operazioni che service devono implementare
 * 
 * @param <E>
 *            Il tipo di oggetto di dominio principale per il quale il service viene creato
 * @param <F>
 *            Il tipo della chiave primario dell'oggetto di dominio
 * @author Riccardo Bocci
 * @author Fabrizio Corsetti
 */
public interface BaseService<E, F extends Serializable> {

    /**
     * Operazione di inserimento
     * 
     * @param entity
     *            l'entità di dominio da inserire
     */
    public void insert(E entity);

    /**
     * Operazione di aggiornamento
     * 
     * @param entity
     *            l'entità di dominio da aggiornare
     */
    public void update(E entity);

    /**
     * Operazione di cancellazione
     * 
     * @param entity
     *            l'entità di dominio da cancellare
     */
    public void delete(E entity);

    /**
     * @see BaseDAO#findAll(Integer, Integer)
     * 
     */
    public List<E> findAll(Integer firstResult, Integer maxResult);

    /**
     * L'operazione di recupero di un oggetto di dominio data una determinata chiave
     * 
     * @param id
     *            la chiave per la quale recuperare l'oggetto di dominio
     * @return l'oggetto di dominio
     */
    public E findById(F id);

    /**
     * Metodo per il recupero di un oggetto di dominio.
     * <ul>
     * <li>Se la chiave primaria è valorizzata, recupera l'oggetto invocando il metodo
     * {@link BaseService#findById(Serializable)}. (rilancia una BusinessValidationException se non trova l'oggetto)</li>
     * <li>Se la chiave primaria non è valorizzata, esegue il metodo
     * {@link BaseServiceImpl#customBindDomainObject(Object)}</li>
     * </ul>
     * 
     * @param entity
     *            oggetto da ricercare (popolato con i campi da utilizzare per la ricerca)
     * @param idClass
     *            classe che rappresenta la chiave primaria
     * @param idPath
     *            path della variabile che contiene il valore della chiave primaria relativamente all'entity
     * @return L'istanza dell'oggetto di dominio ricercato o null se non trovato o se la query restituisce più
     *         risultati.
     * @throws RuntimeException
     *             se il metodo {@link BaseService#findById(Serializable)} restituisce null (record non presente nel db)
     */
    public E bindDomainObject(E entity, Class<?> idClass, String idPath);

    /**
     * @see BaseDAO#newIdFromSequence(Object)
     * 
     */
    public F newIdFromSequencetable(E entity);
}
