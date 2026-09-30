/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipicausalioneriDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface TipicausalioneriService extends BaseService<Tipicausalioneri, PkId> {

    /**
     * @see TipicausalioneriDAO#findAll(Integer, Integer)
     */
    public List<Tipicausalioneri> findAll(Integer firstResult, Integer maxResult);

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
     *            Escludo dalla ricerca questa causale. Può essere null se sto controllando che una causale è usata come
     *            bollo.
     * @return
     */
    public boolean isCausaleBollo(Tipicausalioneri causalebollo, Tipicausalioneri tipicausalioneri);

    /**
     * @see TipicausalioneriDAO#findByDescrizione(String)
     */
    public List<Tipicausalioneri> findByDescrizione(String descrizione, String codiceSoftware);

    /**
     * @see TipicausalioneriDAO#findByDescrizioneAndFlagEndo(String, Boolean)
     */
    public List<Tipicausalioneri> findByDescrizioneAndFlagEndo(String textToSearch, Boolean flagEndo, String codiceSoftware);

    /**
     * Metodo per verificare se il campo importo istruttoria è impostabile per la causale specificata
     * 
     * @param entity
     * @return
     */
    public boolean isImportoIstruttoriaImpostabile(Tipicausalioneri entity);

    /**
     * <pre>
     * Ritorna la lista dei tipi causali oneri filtrati per il campo "flgTipicausaliinteressi"
     * 
     *      se : isInteressiDiMora==false il metodo filtra per flgTipicausaliinteressi==0
     *      se : isInteressiDiMora==true il metodo filtra per flgTipicausaliinteressi==1
     * @param firstResult
     * @param maxResult
     * @param isInteressiDiMora
     * @return
     * </pre>
     */
    public List<Tipicausalioneri> findAll(String idcomune, Integer firstResult, Integer maxResult, boolean isInteressiDiMora);

    /**
     * @see TipicausalioneriDAO#findTipicausalioneriMoraByDescrizione(String textToSearch, String codiceSoftware)
     */
    public List<Tipicausalioneri> findTipicausalioneriMoraByDescrizione(String textToSearch, String codiceSoftware);

    /**
     * Torna una causale oneri associata al codicepeople TIPICAUSALIONERI.CODICECAUSALEPEOPLE. Prima la cerca per il
     * software corrente poi per TT;
     * 
     * @param codiceCausaleStr
     * @return
     */
    public Tipicausalioneri findbByCodiceCausalepeople(String codiceCausaleStr);
}
