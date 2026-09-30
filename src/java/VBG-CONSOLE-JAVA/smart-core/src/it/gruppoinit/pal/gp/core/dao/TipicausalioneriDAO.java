/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface TipicausalioneriDAO extends BaseDAO<Tipicausalioneri, PkId> {

    /**
     * Metodo che ritorna la lista di tipicausalioneri filtrate per IDCOMUNE(passato al primo arg) e SOFTWARE(corrente) e ordinate ASC per codice
     */
    public List<Tipicausalioneri> findAll(String idComune, Integer firstResult, Integer maxResult);

    /**
     * Metodo per recuperare le causali da utilizzare come bollo.<br/>
     * I filtri sono:<br/>
     * - proprietà pagamentiregulus=true<br/>
     * - proprietà causalebollo!=null<br/>
     * 
     * 
     * @param tipicausalioneri
     *            Il valore del parametro può essere null. Se diverso da null vengono ricercate tutte le causali tranne
     *            quella che è stata passata.
     * @return
     */
    public List<Tipicausalioneri> findCausaliBollo(Tipicausalioneri tipicausalioneri);

    /**
     * Metodo per controllare se una causale è stata già usata come causale bollo.
     * 
     * @param causalebollo
     * @param tipicausalioneri
     *            Escludo dalla ricerca questa causale.Può essere null se sto controllando che una causale è usata come
     *            bollo.
     * @return
     */
    public boolean isCausaleBollo(Tipicausalioneri causalebollo, Tipicausalioneri tipicausalioneri);

    /**
     * torna una lista di causali oneri <b>abilitati</b> del modulo software corrente con il criterio di ricerca
     * coDescrizione = ilike %descrizione% ordinati per coDescrizione dalla A alla Z Lista ordinata per:<br/>
     * coOrdinamento asc<br/>
     * coDescrizione asc<br/>
     * 
     * @param descrizione
     * @return
     */
    public List<Tipicausalioneri> findByDescrizione(String descrizione, String codiceSoftware);

    /**
     * torna una lista di causali oneri <b>abilitati</b> con il criterio di ricerca:<br/>
     * coDescrizione = ilike %descrizione% ordinati per coDescrizione dalla A alla Z<br/>
     * flag coSerichiedeendo = flagEndo<br/>
     * software: quello corrente se codicesoftware è null, altrimenti per il codice passato Lista ordinata per:<br/>
     * coOrdinamento asc<br/>
     * coDescrizione asc<br/>
     * 
     * @param flagEndo
     * 
     * @param descrizione
     * @return
     */
    public List<Tipicausalioneri> findByDescrizioneAndFlagEndo(String textToSearch, Boolean flagEndo, String codiceSoftware);

    /**
     * <pre>
     * Ritorna la lista dei tipi causali oneri filtrati per il campo "flgTipicausaliinteressi"
     * 
     *      se : isInteressiDiMora==false il metodo filtra per flgTipicausaliinteressi==0 or flgTipicausaliinteressi==null
     *      se : isInteressiDiMora==true il metodo filtra per flgTipicausaliinteressi==1
     * @param firstResult
     * @param maxResult
     * @param isInteressiDiMora
     * @return
     * </pre>
     */
    public List<Tipicausalioneri> findAll(String idComune, Integer firstResult, Integer maxResult, Boolean isInteressiDiMora);
}
