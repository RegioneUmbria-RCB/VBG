package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwIAttivitalista;

/**
 * 
 * @author
 */
public interface VwIAttivitalistaDAO extends BaseDAO<VwIAttivitalista, PkId> {

    /**
     * 
     * 
     */
    public List<VwIAttivitalista> findAll(Integer firstResult, Integer maxResult);

    /**
     * Il metodo torna la lista delle attività che hanno la stessa denominazione di quella passata. Può essere passata
     * la lista dei software in cui controllare e se devono essere considerate solamente le attività attive o no
     * 
     * @param software
     * @param checkAttiva
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Integer> findBydenominazione(String denominazione, String[] software, boolean checkAttiva, Integer firstResult, Integer maxResult);

    /**
     * Il metodo torna la lista delle attività che hanno la stessa localizzazione di quella passata. Può essere passata
     * la lista dei software in cui controllare e se devono essere considerate solamente le attività attive o no
     * 
     * @param localizzazione
     * @param software
     * @param checkAttiva
     * @param firstResult
     * @param maxResult
     * @return
     */
    List<Integer> findByLocalizzazione(Istanzestradario localizzazione, String[] software, boolean checkAttiva, Integer firstResult,
	    Integer maxResult);
}
