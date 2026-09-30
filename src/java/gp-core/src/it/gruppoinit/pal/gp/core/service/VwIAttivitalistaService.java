package it.gruppoinit.pal.gp.core.service;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.VwIAttivitalistaDAO;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwIAttivitalista;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

/**
 * 
 * @author
 */
public interface VwIAttivitalistaService extends BaseService<VwIAttivitalista, PkId> {

    /**
     * @see VwIAttivitalistaDAO#findAll(Integer, Integer)
     */
    public List<VwIAttivitalista> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see VwIAttivitalistaDAO#findByFilterTable(FilterTable)
     * @param filterTable
     * @return
     */
    public List<VwIAttivitalista> findByFilterTable(FilterTable filterTable);

    /**
     * @see VwIAttivitalistaDAO#findByFilterTable(FilterTable)
     * @param filterTable
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<VwIAttivitalista> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult);

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
     * Il metodo torna la lista delle attività che hanno almeno una localizzazione in comune con quelle passate. Può
     * essere passata la lista dei software in cui controllare e se devono essere considerate solamente le attività
     * attive o no
     * 
     * @param localizzazioni
     * @param software
     * @param checkAttiva
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Integer> findByLocalizzazioni(Set<Istanzestradario> localizzazioni, String[] software, boolean checkAttiva, Integer firstResult,
	    Integer maxResult);
}
