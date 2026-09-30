package it.gruppoinit.stc.service;

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
     * L'operazione di ricerca di tutti i record senza particolari filtri per una data entità di dominio.
     * 
     * @param firstResult
     *            il primo record da recuperare, partendo da 0 ( può essere nullo )
     * @param maxResult
     *            il numero massimo di records da recuperare ( può essere nullo )
     * @return una <code>java.util.List</code> di oggetti di dominio
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
}
