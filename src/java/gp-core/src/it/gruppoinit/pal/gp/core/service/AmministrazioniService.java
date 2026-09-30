/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.AmministrazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AmministrazioniHelper;

/**
 * @author francescop
 * @author gianpaolot
 */
public interface AmministrazioniService extends BaseService<Amministrazioni, PkId> {

    /**
     * Restituisce la lista di tutte le amministrazioni filtrate per idcomune tranne quelle con codice: <br />
     * (-1)TUTTE LE AMMINISTRAZIONI e (-2)LA STESSA AMMINISTRAZIONE
     * 
     * @see BaseService#findAll(Integer, Integer)
     */
    public List<Amministrazioni> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ricerca le amministrazioni per descrizione dell'amministrazione
     * 
     * @param amministrazione
     *            la descrizione per la quale ricercare l'amministrazione
     * @param tutteLeAmministrazioni
     *            se true allora cerca anche le amministrazioni con Id negativo (-1 tutte le amministrazioni, -2 la
     *            stessa amministrazione)
     * @param includiDisabilitate
     *            se true allora recupera anche quelle con AMMINISTRAZIONI.FLAG_DISABILITATO=1
     * @param codiciAmministrazioniEscluse
     *            la lista dei codici amministrazione da escludere dalle ricerche (può essere null)
     * @return
     */
    public List<Amministrazioni> findByAmministrazione(String amministrazione, boolean tutteLeAmministrazioni, boolean includiDisabilitate,
	    Integer[] codiciAmministrazioniEscluse);

    /**
     * Metodo che ricerca le amministrazioni per descrizione
     */
    public List<Amministrazioni> findAmministrazioniByDescrizione(String amministrazione);

    /**
     * @see AmministrazioniDAO#findAmministrazioniByDescrizioneForProtocolloRegistri(String amministrazione)
     * @param includiDisabilitate
     *            se true allora recupera anche quelle con AMMINISTRAZIONI.FLAG_DISABILITATO=1
     */
    public List<Amministrazioni> findAmministrazioniByDescrizioneForProtocolloRegistri(String amministrazione, boolean includiDisabilitate,
	    String codiceComune, String software);

    /**
     * Metodo per recuperare tutte le amministrazioni interne tranne quelle con codice: <br />
     * (-1)TUTTE LE AMMINISTRAZIONI e (-2)LA STESSA AMMINISTRAZIONE
     * 
     * @return
     */
    public List<Amministrazioni> findByAmministrazioniInterne();

    /**
     * @see AmministarzioniDAO#isAmministrazioneInternaEsiste()
     */
    public boolean isAmministrazioneInternaEsiste();

    /**
     * @see AmministarzioniDAO#findAllDTO(Integer firstResult, Integer maxResult, DAOEnum whereClauseMandatoryFields,
     *      String orderProperty, DAOOrderTypeEnum orderType)
     */
    public List<AmministrazioniHelper> findAllDTO(Integer firstResult, Integer maxResult, DAOEnum whereClauseMandatoryFields, String orderProperty,
	    DAOOrderTypeEnum orderType);

    public Amministrazioni findAmministrazioniByCodiceancitel(String codiceancitel);

    /**
     * ricerca tutte le amministrazioni configurate per STC (idnodo, idente, idsportello valorizzati)
     * 
     * @return
     */
    public List<Amministrazioni> findAmministrazioniSTC();

    /**
     * Torna un'amministrazione, se presente, configurata con questi parametri. Qualora ne trovasse più di una, verifica
     * se almeno una coincide con l'amministrazione mittente. Se non coincidono o se l'amministrazione mittente non
     * viene passata, torna la prima della lista ( ordinata per codiceamministrazione desc ).
     * 
     * @param idNodo
     * @param idEnte
     * @param idSportello
     * @param codiceAmministrazioneMittente
     * @return
     */
    public Amministrazioni findAmministrazioneSTC(String idNodo, String idEnte, String idSportello, Integer codiceAmministrazioneMittente);

    /**
     * Verifica se ci sono più amministrazioni configurate come nodi con la stessa tupla
     * 
     * @param idNodo
     * @param idEnte
     * @param idSportello
     * @return
     */
    public int countAmministrazioniSTC(String idNodo, String idEnte, String idSportello);

    /**
     * Torna l'amministrazione che identifica lo sportello unico --> Configurazione.codammsportellounico
     * 
     * @return
     */
    public Amministrazioni findAmministrazioneSportelloUnico();

    /**
     * Il metodo controlla se è possibile disabilitare il record corrente. E' possibile disabilitare un record di
     * Amministrazioni se non è configurata nessuna dipendenza nelle tabelle di configurazione. ad oggi
     * <fieldset><legend>Tabelle per le quali viene effettuato il controllo</legend>
     * <ul>
     * <li>COMMEDILIZIE_TIPOLOGIE.CODICEAMMINISTRAZIONE</li>
     * <li>INVENTARIOPROCEDIMENTI.AMMINISTRAZIONE</li>
     * <li>PROTOCOLLO_REGISTRI.IDDESTINATARIO</li>
     * <li>PROTOCOLLO_REGISTRI.IDMITTENTE</li>
     * <li>TIPIMOV_STC_ALBEROPROC.CODICEAMMINISTRAZIONE</li>
     * <li>TIPIMOV_STC_ALTRIDATI.CODICEAMMINISTRAZIONE</li>
     * <li>TIPIMOV_STC_MAPPING.PROTOCOLLO_MITTENTE</li>
     * <li>TIPIMOV_STC_MAPPING.CODICEAMMINISTRAZIONE</li>
     * <li>TIPIMOV_STC_MODELLI.CODICEAMMINISTRAZIONE</li>
     * </ul>
     * </fieldset><br />
     * <fieldset><legend>Tabelle per le quali non viene effettuato il controllo</legend>
     * <ul>
     * <li>TEMPIRISPOSTA.CODICEAMMINISTRAZIONE (Controllo effettuato nell'elaborazione)</li>
     * <li>TIPICONTROMOVIMENTO.AMMMOV (Controllo effettuato nell'elaborazione)</li>
     * <li>TIPICONTROMOVIMENTO.AMMRITSU (Controllo effettuato nell'elaborazione)</li>
     * <li>DOCUMENTI.AMMINISTRAZIONE (Documenti degli endo - il campo è dismesso e non viene verificato)</li>
     * <li>ALLEGATI.AMMINISTRAZIONE (Allegati degli endo - il campo è dismesso e non viene verificato)</li>
     * <li>AMMINISTRAZIONIREFERENTI.CODICEAMMINISTRAZIONE (Non Controllato)</li>
     * <li>AMMINISTRAZIONIRESPONSABILI.AMMINISTRAZIONIRESPONSABILI (Non Controllato)</li>
     * <li>AMMINISTRAZIONIRUOLI.CODICEAMMINISTRAZIONE (Non Controllato)</li>
     * </ul>
     * </fieldset><br />
     * <fieldset><legend>Tabelle per le quali non viene effettuato il controllo in quanto non sono di
     * configurazione</legend>
     * <ul>
     * <li>ALBO_PUBBLICAZIONI.CODICEAMMINISTRAZIONE</li>
     * <li>CDSINVITATI.CODICEAMMINISTRAZIONE</li>
     * <li>COMMEDILIZIE_APPELLO.CODICEAMMINISTRAZIONE</li>
     * <li>EMAIL.CODICEAMMINISTRAZIONE</li>
     * <li>ISTANZEONERI.CODICEAMMINISTRAZIONE</li>
     * <li>MOVIMENTI.CODICEAMMINISTRAZIONE_STC</li>
     * <li>MOVIMENTI.MOVIMENTI</li>
     * </ul>
     * 
     * @param amministrazioni
     * @return
     */
    public boolean checkSeDisabilitare(Amministrazioni amministrazioni);

    /**
     * Recupera l'amministrazione che ha come codice amministrazione cart quella passata
     * 
     * @param amministrazioneCart
     * @return
     */
    public Amministrazioni findByCodiceamministrazioneCart(String amministrazioneCart);

    /**
     * <pre>
     * Il metodo confronta i due array e per ogni codice amministrazione presente, recupera l'amminitrazione dal db e popola il campo
     * codice_cartcon ilvalore corrispondente dell'array "codiciAmministrazioniCart".
     * Es. codiciAmministrazioni = {" ","4","5"} ,codiciAmministrazioniCart= {"ASL","VF","ARPA","ED"} il sistema seguira questa logica:
     * <ol>
     * 		<li>primo elemeneto di "codiciAmministrazioni" è null , salto al secondo </li>
     * 		<li>secondo elemeneto di "codiciAmministrazioni" è 4 , assoccio all'amministrazione codice 4 il valore "VF" </li>
     * 		<li>secondo elemeneto di "codiciAmministrazioni" è 5 , assoccio all'amministrazione codice 5 il valore "ARPA" </li>	
     * </ol>
     * In un secondo momento il metodo controlla se nel sistema esistono amministrazionicon i valori di  "codiciAmministrazioniCart",
     * se già sono presenti:
     * <ol>
     * 	   <li>presenti allora return <b>true</b></li>
     * 	   <li>NON presenti allora return <b>false</b></li>				
     * </ol>
     * &#64;param codiciAmministrazioni
     * &#64;param codiciAmministrazioniCart
     * &#64;return
     * </pre>
     */
    public boolean updateAmministrazioniCartAndValidateConfiguration(String[] codiciAmministrazioni, String[] codiciAmministrazioniCart);

    /**
     * <pre>
     * Il metodo confronta i due array e per ogni codice amministrazione cart presente, recupera l'amminitrazione dal db (se esiste) con il codice_cart passato 
     * e popola il campo tipomovimeto_cart con il valore corrispondente dell'array "codiciTipoMov".
     * Es. codiciTipoMov = {" ","4","5"} ,codiciAmministrazioniCart= {"ASL","VF","ARPA","ED"} il sistema seguira questa logica:
     * <ol>
     * 		<li>primo elemeneto di "codiciTipoMov" è null, salto al secondo </li>
     * 		<li>secondo elemeneto di "codiciTipoMov" è 4,controllo se per esiste un amministrazione con "VF" , se si popolo il campo tipomovimeto_cart altrimenti salto  </li>
     * 		<li>secondo elemeneto di "codiciTipoMov" è 5,controllo se per esiste un amministrazione con "ARPA" , se si popolo il campo tipomovimeto_cart altrimenti salto  </li>
     * </ol>
     * In un secondo momento il metodo controlla se nel sistema esistono amministrazionicon i valori di  "codiciAmministrazioniCart",
     * se già sono presenti:
     * <ol>
     * 	   <li>presenti allora return <b>true</b></li>
     * 	   <li>NON presenti allora return <b>false</b></li>				
     * </ol>
     * &#64;param codiciTipoMov
     * &#64;param codiciAmministrazioniCart
     * &#64;return
     * </pre>
     */
    public boolean updateTipimovimentoCartAndValidateConfiguration(String[] codiciTipoMov, String[] codiciAmministrazioniCart);

    public List<Amministrazioni> findAmministrazioniWithEmailByDescrizione(String amministrazione);

    /**
     * Restituisce una {@link List} di {@link Amministrazioni} aventi indirizzo PEC uguale a quello passato come
     * argomento. Vengono sempre escluse dai risultati le amministrazioni che hanno id negativo: (-1)TUTTE LE
     * AMMINISTRAZIONI e (-2)LA STESSA AMMINISTRAZIONE. Anche le amministrazioni disabilitate sono escluse dai risultati
     * della ricerca.
     * 
     * @param pecAddress
     * @return
     */
    public List<Amministrazioni> findAmministrazioniByPECAddress(String pecAddress);

    /**
     * 
     * @param codiceComune
     * @return
     */
    public List<Amministrazioni> findByCodicecomune(String codiceComune, Integer firstResult, Integer maxResults);

    public void aggiornaMailEPec(Integer codiceAmministrazione, String email, String pec);
}
