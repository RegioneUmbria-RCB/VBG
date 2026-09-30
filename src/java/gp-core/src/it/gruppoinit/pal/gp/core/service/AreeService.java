package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.AreeDAO;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * Il service lavora in modalità <b>"Comuni associati"</b> <br/>
 * Tutti i metodi devono rilanciare un'eccezione se l'operatore non ha comuni associati (record nella tabella
 * responsabilicomuni).
 * 
 * @author Riccardo Bocci
 * 
 */
public interface AreeService extends BaseService<Aree, PkId> {

    public List<Aree> findByFilterTable(FilterTable filterTable);

    /**
     * Il metodo controlla, nel caso di installazioni in modalità <b>"Comuni associati"</b> che l'operatore abbia i
     * permessi sui comuni. Nel caso contrario rilancia un'eccezione.
     * 
     * @param descrizione
     * @param codiceComune
     * @see AreeDAO#findByDescrizione(String, String, String[])
     */
    public List<Aree> findByDescrizione(String descrizione, String codiceComune);

    /**
     * Il metodo controlla, nel caso di installazioni in modalità <b>"Comuni associati"</b> che l'operatore abbia i
     * permessi sui comuni. Nel caso contrario rilancia un'eccezione.
     * 
     * @see AreeDAO#findAll(Integer, Integer)
     */
    public List<Aree> findAll(Integer firstResult, Integer maxResult);

    /**
     * Controlla se esistono i record nella tabella
     * 
     * @param filterTable
     * @return
     */
    public boolean existsRecords();

    @DeletableCacheElements
    public void resetObjectCached();

    /**
     * Restituisce tulle le aree che sono già settate per l'istanza passata
     * 
     * @param istanza
     *            : filtro applicato per trovare solo i record dell'istanza cercata
     * @return Lista di Aree
     */
    public List<Aree> findAreeByIstanza(Istanze istanza);

    /**
     * 
     * @param descrizione
     * @param codiceComune
     * @see AreeDAO#findByDescrizioneDehors(String, String, String[])
     */
    public List<Aree> findByDescrizioneDehors(String textToSearch, String codiceComune);
}
