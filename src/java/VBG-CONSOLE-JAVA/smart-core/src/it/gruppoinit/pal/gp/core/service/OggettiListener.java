package it.gruppoinit.pal.gp.core.service;

/**
 * Interfaccia che espone i metodi che i DAO che si occupano di oggetti di dominio devono implementare alla
 * modifica/cancellazione di oggetti
 * 
 * @author riccardob
 * 
 * @param <E>
 */
public interface OggettiListener<E> {

    /**
     * Questo metodo viene invocato nel metodo delete di BaseDAO
     * 
     * @param entity
     */
    void onDeleteOggetti(E entity);

    /**
     * Questo metodo viene invocato nei metodi insert update di BaseDAO
     * 
     * @param entity
     */
    void onModifyOggetti(E entity);
}
