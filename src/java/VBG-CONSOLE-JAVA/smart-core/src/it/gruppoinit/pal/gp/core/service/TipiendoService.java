package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

public interface TipiendoService extends BaseService<Tipiendo, PkId> {

    /**
     * Metodo per le ricerche Ajax. Filtro per il "like" della descrizione o per il codice e per il codice
     * Tipifamiglieendo
     * 
     * @param textToSearch
     * @param codiceFamiglia
     *            non è obbligatorio
     * @return
     */
    public List<Tipiendo> findByDescSWeTT(String textToSearch, Integer codiceFamiglia);

    /**
     * Recupera una lista di categorie endo filtrate per tipo famiglia endo
     * 
     * @param tipifamiglieendo
     * @return
     */
    public List<Tipiendo> findByTipiFamiglieEndo(Tipifamiglieendo tipifamiglieendo);

    /**
     * Metodo per le ricerche Ajax. Filtro per il "like" della descrizione o per il codice, per il codice
     * Tipifamiglieendo e per software (se non viene passato filtra per il software corrente)
     * 
     * @param textToSearch
     * @param codiceFamiglia
     *            non è obbligatorio
     * @param codicesoftware
     *            non è obbligatorio, se non passato usa il software presente sull'OMRHelper
     * @return
     */
    public List<Tipiendo> findByDescAndSW(String textToSearch, Integer codiceFamiglia, String[] codicesoftware, String idcomunebase);

    /**
     * Torna il conteggio dei tipiendo per filtrati per tipo famiglia endo
     * 
     * @param tipifamiglieendo
     * @return
     */
    public int countByTipiFamiglieEndo(Tipifamiglieendo tipifamiglieendo);

    /**
     * Torna una lista di categoria endo (Tipi endo ) filtrati per i campi dell'oggetto impostati.
     * 
     * @param tipiendo
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Tipiendo> findByTipiendo(Tipiendo tipiendo, Integer firstResult, Integer maxResult);

    /**
     * <p>
     * Converte l'oggetto tipi endo in una filter table da impostare per una query di ricerca.<br>
     * <b>Campi implementati</b>
     * <ul>
     * <li>idcomune</li>
     * <li>software</li>
     * <li>tipifamiglieendo</li>
     * </ul>
     * 
     * @param tipiendo
     * @return </p>
     */
    public FilterTable getTipiendoToFilterTable(Tipiendo tipiendo);

    public List<Tipiendo> findTipiendoByDescrizione(String testoDaCercare, String tipoRicerca, String campiRicerca, Integer firstResult,
	    Integer maxResult);
}
