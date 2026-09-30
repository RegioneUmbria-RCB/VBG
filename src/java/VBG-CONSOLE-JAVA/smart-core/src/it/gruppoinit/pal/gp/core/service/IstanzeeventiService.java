package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzeeventiDAO;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeeventiListHelper;
import it.gruppoinit.pal.gp.core.domain.web.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeeventiFilter;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface IstanzeeventiService extends BaseService<Istanzeeventi, PkId> {

    /**
     * @see IstanzeeventiDAO#findAll(Integer, Integer)
     */
    public List<Istanzeeventi> findAll(Integer firstResult, Integer maxResult);

    /**
     * recupera tutti gli eventi non letti e visualizzabili dall'operatore passato secondo la seguente logica:<br />
     * se l'evento è associato ad un'istanza lo visualizzo solo se l'accesso all'istanza è consentito.<br />
     * se l'evento è associato ad un movimento lo visualizzo solo se l'accesso al movimento è consentito.
     * 
     * Gli eventi sono ordinati per data. vedi:
     * 
     * @param responsabile
     * 
     * @see IstanzeeventiDAO#findAll(Integer, Integer)
     */
    public List<Istanzeeventi> findAllByResponsabile(Responsabili responsabile, Integer firstResult, Integer maxResult);

    /**
     * @see IstanzeeventiDAO#findByFilter(IstanzeeventiFilter)
     * @param filter
     * @return
     */
    public List<Istanzeeventi> findByFilter(IstanzeeventiFilter filter, Integer firstResult, Integer maxResult);

    /**
     * Inserisce un evento generico per il software e la categoria specificati
     * 
     * @param descrizione
     * @param idCategoria
     * @param software
     */
    public void insertEventoBackoffice(String descrizione, String idCategoria, Software software);

    public List<Istanzeeventi> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult);

    /**
     * 
     * @param batchScadenzarioFilter
     * @param i
     * @param j
     * @return
     */
    public List<Istanzeeventi> findAllByScadenzarioFilter(BatchScadenzarioFilter batchScadenzarioFilter, Integer firstResult, Integer maxResult);

    public List<IstanzeeventiListHelper> findByScadenzarioFilterHelper(BatchScadenzarioFilter batchScadenzarioFilter, Integer firstResult,
	    Integer maxResult);

    public int countByScadenzarioFilterHelper(BatchScadenzarioFilter batchScadenzarioFilter);

    public void clear();

    /**
     * Ritorna il numero di eventi di sistema (eventi con codice istanza null and codice movimento null). Gli venti sono
     * filtrati per il software o per tutti i software abilitati per l'operatore
     * 
     * @param responsabile
     *            se diverso da null filtra e software == TT usa responsabile per filtrare su tutti i software abilitati
     *            all'operatore
     * @return
     */
    public int countByEventiSistema(Responsabili responsabile, boolean isLetto);

    /**
     * Ritorna la lista eventi di sistema (eventi con codice istanza null and codice movimento null) Gli venti sono
     * filtrati per il software o per tutti i software abilitati per l'operatore
     * 
     * @param firstResult
     * @param maxResult
     * @param responsabile
     *            se diverso da null filtra e software == TT usa responsabile per filtrare su tutti i software abilitati
     *            all'operatore
     * @return
     */
    public List<Istanzeeventi> findEventiSistema(Responsabili responsabile, Integer firstResult, Integer maxResult, boolean isLetto);

    /**
     * Ritorna il numero di eventi non letti per il movimento passato
     * 
     * @param codice
     * @return
     */
    public int countEventiNonLettiByMovimento(Integer codice);
}
