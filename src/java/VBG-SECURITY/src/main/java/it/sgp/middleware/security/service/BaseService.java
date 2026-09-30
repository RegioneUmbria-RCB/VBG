package it.sgp.middleware.security.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import it.sgp.middleware.security.domain.ComunisecurityApp;

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
     * Operazione di inserimento diretto senza SELECT preventiva
     * 
     * @param entity
     *            l'entità di dominio da inserire
     */
    public void insertNoCheck(E entity);

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
     * 
     * 
     */
    public List<E> findAll();

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
     * metodo per il recupero degli errori di validazione dello strato Service
     * 
     * @return
     */
    // public InvalidValue[] getValidationMessages();
    public Page<E> findAllByExamplePaginated(PageRequest pageable, Example<E> exampleFromRequest);
}