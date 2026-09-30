package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;

import java.io.Serializable;

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
     * L'operazione di recupero di un oggetto di dominio data una determinata chiave
     * 
     * @param id
     *            la chiave per la quale recuperare l'oggetto di dominio
     * @return l'oggetto di dominio
     */
    public E findById(F id);

    /**
     * @see BaseDAO#newIdFromSequence(Object)
     * 
     */
    public F newIdFromSequencetable(E entity);
}
