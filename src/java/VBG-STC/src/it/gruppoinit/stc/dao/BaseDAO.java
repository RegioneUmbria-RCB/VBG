package it.gruppoinit.stc.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Interfaccia che contiene le operazioni che i DAO devono implementare. <br />
 * Questa interfaccia espone i principali metodi CRUD che un DAO dovrebbe avere di default
 * 
 * @param <E>
 *            L'entity associata alla tabella su cui operare
 * @param <F>
 *            Il tipo della proprietà dell'entity che rappresenta la chiave primaria della tabella associata
 * @author Riccardo Bocci
 * @author Fabrizio Corsetti
 */
public interface BaseDAO<E, F extends Serializable> {
    /**
     * Inserisce un record nella tabella associata all'entity con le informazioni presenti nell'entity
     * 
     * @param entity
     *            l'entity da inserire
     */
    public void insert(E entity);

    /**
     * Aggiorna un record della tabella associata all'entity con le informazioni presenti nell'entity
     * 
     * @param entity
     *            l'entity da aggiornare
     */
    public void update(E entity);

    /**
     * Cancella un record dalla tabella associata all'entity tramite l'uso della proprietà dell'entity che rappresenta
     * la chiave primaria della tabella
     * 
     * @param entity
     *            l'entity relativa al record da cancellare
     */
    public void delete(E entity);

    /**
     * Ricerca tutti i record di una determinata tabella per l'idcomune corrente
     * 
     * @param firstResult
     *            il primo record da recuperare, partendo da 0 ( può essere nullo )
     * @param maxResult
     *            il numero massimo di records da recuperare ( può essere nullo )
     * @return una <code>java.util.List</code> di entity
     */
    public List<E> findAll(Integer firstResult, Integer maxResult);

    /**
     * Recupera un record della tabella associata all'entity
     * 
     * @param id
     *            la chiave per la quale recuperare l'oggetto di dominio
     * @return l'entity
     */
    public E findById(F id);

    /**
     * Metodo astratto che le classi che estendono <code>BaseServiceImpl</code> devono implementare per identificare
     * l'entity sulla quale eseguire i metodi
     * 
     * @return la classe dell'entity
     */
    public Class<E> getEntityClass();
}
