package it.gruppoinit.pal.gp.core.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.AnagrafeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeRicercaBean;
import it.gruppoinit.pal.gp.core.dao.helper.RicercaAnagraficeCollegateEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeAvvisiHelper;
import it.gruppoinit.pal.gp.core.domain.web.AnagrafeFilter;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DettaglioAnagrafeRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.FiltroSoggetti;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.anagrafe.model.CodiceVerificaMailAnagrafeBean;
import it.gruppoinit.pal.gp.core.features.anagrafica.eventi.EventoEmailAnagrafeAggiornata;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoOperazioneAggiornamento;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.helper.TipoWSAnagrafeAttivoEnum;

public interface AnagrafeService extends BaseService<Anagrafe, PkId> {

    /**
     * Ricerca tutti i record della tabella anagrafe che risultano attivi (flag_disabilitato=0 o null)
     * 
     * @param anagrafe
     * @return
     */
    public List<Anagrafe> findActiveByFilter(Anagrafe anagrafe);

    /**
     * Ricerca tutti i record della tabella anagrafe
     * 
     * @param anagrafe
     * @return
     */
    public List<Anagrafe> findAllByFilter(Anagrafe anagrafe);

    /**
     * Ricerca tutti i record della tabella anagrafe che risultano disabilitati (flag_disabilitato=1)
     * 
     * @param anagrafe
     * @return
     */
    public List<Anagrafe> findDisabledByFilter(Anagrafe anagrafe);

    /**
     * Ricerca tutti i record della tabella anagrafe identificati dal filtro
     * 
     * @param anagrafe
     *            il filtro
     * @return
     */
    public List<Anagrafe> findByFilter(AnagrafeFilter filtro);

    public List<Anagrafe> findAnagrafeByRegistrazioni(Anagrafe entity);

    /**
     * @see AnagrafeDAO#findAll(Integer firstResult, Integer maxResult)
     */
    public List<Anagrafe> findAll(Integer firstResult, Integer maxResult);

    /**
     * Cerca un ANAGRAFESTORICO che abbia dataInizioValidita minore/uguale alla data passata e dataFineValidita
     * maggiore/uguale alla data passata se non trovata ripete la ricerca con dataFineValidita=null se non trovata
     * eccezione perchè non è presente
     * 
     * @param entity
     * @param dataValidita
     * @return
     */
    public Anagrafestorico findStoricoId(Anagrafe entity, Date dataValidita);

    /**
     * Cerca un ANAGRAFESTORICO che abbia dataInizioValidita minore/uguale alla data passata e dataFineValidita
     * maggiore/uguale alla data odierna se non trovata ripete la ricerca con dataFineValidita=null se non trovata
     * eccezione perchè non è presente
     * 
     * @param entity
     * @return
     */
    public Anagrafestorico findStoricoId(Anagrafe entity);

    /**
     * Calcola il codice fiscale a partire dei dati passati (tutti obbligatori)
     * 
     * @param nominativo
     * @param nome
     * @param datanascita
     * @param sesso
     * @param codicecomune
     * @return
     */
    public String calcolaCodicefiscale(String nominativo, String nome, Date datanascita, String sesso, String codicecomune);

    /**
     * Restituisce campi dell'anagrafe aggiornati richiedendoli tramite WS a un ente terzo passandogli il codice
     * fiscale. Se non viene passato il codice fiscale il metodo ritorna un oggetto null
     * 
     * @param request
     * @param anagrafe
     * @return
     */
    public Anagrafe findAnagrafeAggiornataByCF(Anagrafe anagrafeSigepro);

    /**
     * Restituisce campi dell'anagrafe aggiornati richiedendoli tramite WS a un ente terzo passandogli la partita iva.
     * Se non viene passato il codice fiscale il metodo ritorna un oggetto null
     * 
     * @param request
     * @param anagrafe
     * @return
     */
    public Anagrafe findAnagrafeAggiornataByPI(Anagrafe anagrafeSigepro);

    public List<Anagrafe> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult);

    public int countRecord(FilterTable filterTable);

    public Anagrafe insertNewStoricoFromAnagrafe(Anagrafe entity, Anagrafe oldAnagrafe, Date dataInizioValidità);

    /**
     * Ritorna un oggetto filter Table a partire dalla entity
     */
    public FilterTable createFilterTableByEntity(Anagrafe anagrafe);

    public List<Anagrafe> findByDescrizione(String descrizione, String tipoAnagrafe, AnagrafeEnum statoAnagrafe, Integer firstResult,
	    Integer maxResult);

    /**
     * Converte l'anagrafe passata da fisica a giuridica
     * 
     * @param anagrafe
     */
    public void convertPersonaFisicaToGiuridica(Anagrafe anagrafe);

    /**
     * Converte l'anagrafe passata da fisica a giuridica
     * 
     * @param anagrafe
     */
    public void convertPersonaGiuridicaToFisica(Anagrafe anagrafe);

    public Anagrafe findDatiAnagrafeDaWs(String cfPivaRicercaWs, Anagrafe anagrafe);

    public Anagrafe findDatiAnagrafeDaWs(String cfPivaRicercaWs, Anagrafe anagrafe, boolean rilanciaEccezione);

    public List<Anagrafestorico> findStorico(Anagrafe anagrafe);

    /**
     * Recupera il record dell'amagrafe storico per l'id passato
     * 
     * @param id
     * @return
     */
    public Anagrafestorico findAnagrafeStoricoById(PkId id);

    /**
     * La funzione torna la lista delle anagrafiche associate ad una istanza presenti in ISTANZE.CODICERICHIEDENTE,
     * ISTANZE.CODICETITOLARELEGALE, ISTANZE.CODICEPROFESSIONISTA, ISTANZERICHIEDENTI.CODICERICHIEDENTE,
     * ISTANZERICHIEDENTI.CODICEANAGRAFECOLL, ISTANZERICHIEDENTI.CODICEPROCURATORE
     * 
     * @param textToSearch
     * @param codiceIstanza
     * @param tipoAnagrafe
     * @return
     */
    public List<AnagrafeRicercaBean> findRichiedentiByIstanza(String textToSearch, Integer codiceIstanza, String tipoAnagrafe);

    /**
     * Restituisce campi dell'anagrafe aggiornati da una richiesta proveniente da Frontend (FO_RICHIESTE)
     * 
     * @return
     */
    public Anagrafe findAnagrafeAggiornataByFE(Integer codiceFoRichiesta);

    public Anagrafe popolateAnagrafeByFoRichiesta(Integer codiceFoRichiesta);

    public void leggiXmlFoRichiesta(byte[] file, it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafeFrontend);

    /**
     * Elimina l'anagrafe storico passato (solo se non utilizzato nelle istanze)
     */
    public void deleteAnagrafeStorico(Integer codiceAnagrafeStorico);

    public List<Integer> findCodiciAnagrafe();

    public AnagrafeAvvisiHelper findAvvisiAnagrafe(Integer codiceAnagrafe);

    public Anagrafedocumenti insertVisuraParix(Integer codiceAnagrafe, Integer codiceIstanza);

    public List<Anagrafe> findByFormegiuridiche(Integer codice, int firstResult, int maxResults);

    public List<Anagrafe> findByTitoli(Integer codice, int firstResult, int maxResults);

    public void clear();

    /**
     * @see AnagrafeDAO#updateAbiltaOrDisabilita(Integer codiceanagrafe, Boolean stato)
     */
    public void updateAbiltaOrDisabilita(Integer codiceanagrafe, Boolean stato);

    public void updateCfAnagrafe(Integer codiceanagrafe, String cf);

    /**
     * Invia una mail all'anagrafe individuata dal codiceanagrafe utilizzando la mail tipo m. Se
     * forzaCreazionePassword==true e la password di anagrafe è vuota ne genera una nuova l'indirizzo è anagrafe.pec e
     * se non trovato anagrafe.pec
     * 
     * @param m
     * @param codiceAnagrafe
     * @param forzaCreazionePassword
     * @return
     */
    public boolean inviaMailUtente(Mailtipo m, Integer codiceAnagrafe, boolean forzaCreazionePassword) throws FunzioneBusinessRemotaException;

    public void updateIdentificato(Integer codiceAnagrafe, Responsabili responsabile);

    public void updateRimuoviIdentificato(Integer codiceAnagrafe, Responsabili responsabile);

    public List<Anagrafe> findByCf(String codiceFiscale, String tipoAnagrafe, boolean escludiDisabilitati);

    /**
     * <pre>
     * 		Il metodo cerca un ' anagrafica attraverso la PI passata.
     * 		Se il campo iscercaPIinCFAzienda: 
     * 
     * 			true: la ricerca verrà fatta sul campo Pi e CF in OR
     *  		false: solo sul campo PI
     *  
     * 		Il parametro  escludiDisabilitati permette:
     * 	
     * 			true: esclude quelli disabilitati
     *  		false: cerca anche tra i disabilitati
     * 
     * 		&#64;param partitaIva
     * 		&#64;param escludiDisabilitati
     * 		&#64;param iscercaPIinCFAzienda
     * 	 &#64;return
     * </pre>
     */
    public List<Anagrafe> findByPI(String partitaIva, boolean escludiDisabilitati, boolean iscercaPIinCFAzienda);

    /**
     * Il metodo richiama uno dei servizi di visura configurati:
     * 
     * 1. PARIX_GATE (Servizione nazionale) 2. ADRIER (Servizio Emilia romagna)
     * 
     * Nel caso siano attivi entrambi la precedenza viene assegna a ADRIER
     * 
     * @return
     */
    public String getDettaglioImpresa(String provinciaREA, String numeroREA) throws FunzioneBusinessRemotaException;

    public String getRicercaImpreseNoncessateByCodiceFiscale(String codiceFiscale) throws FunzioneBusinessRemotaException;

    public void evict(Anagrafe entity);

    /**
     * Riporta se il servizio ws anagrafe è attivo e quale
     * 
     * @return
     */
    public TipoWSAnagrafeAttivoEnum findServizioWSAnagrafeAttivo();

    /**
     * Ritorna la rappresentazione html della visura PARIX
     * 
     * @param cfImpresa
     * @return
     */
    public String visuraParixHTML(String cfImpresa);

    /**
     * torna la lista delle anagrafiche che abbiano registrato almeno una autorizzazione/concessione
     * 
     * @param textToSearch
     * @param object
     * @param active
     * @param i
     * @param numMaxResults
     * @return
     */
    public List<Anagrafe> findAnagraficheConAutorizzazione(String descrizione, String tipoAnagrafe, AnagrafeEnum statoAnagrafe, Integer firstResult,
	    Integer maxResult);

    public boolean inviaMailResetCredenziali(String oggetto, String corpo, String email, String cf, Integer codiceAnagrafe)
	    throws FunzioneBusinessRemotaException;

    /**
     * @see #findCodiciAnagraficheCollegate(Integer, RicercaAnagraficeCollegateEnum)
     * @return
     */
    public List<Anagrafe> findAnagraficheCollegate(Integer codiceAnagrafe, RicercaAnagraficeCollegateEnum contesto);

    /**
     * Dato il codiceAnagrafe vengono trovate le anagrafiche collegate a quel codice. La logica di ricerca è che si
     * trovano tutti i codici anagrafi legati al CF della anagrafe trovata e quindi persone fisiche/giuridiche. Nel caso
     * di CSI viene invocato un servizio che permette di leggere le aziende collegate al cf dell'anagrafe indicato come
     * parametro
     * 
     * @param codiceAnagrafe
     * @param contesto
     *            nel caso di ricerca interna il contesto permette di specificare se le anagrafiche collegate tramite
     *            istanza e autorizzazioni (TUTTE LE AUTORIZZAZIONI, SOLO QUELLE LEGATE AI MERCATI/FIERE, TUTTE LE
     *            ISTANZE ANCHE SENZA AUTORIZZAZIONI)
     * @return
     */
    public Set<Integer> findCodiciAnagraficheCollegate(Integer codiceAnagrafe, RicercaAnagraficeCollegateEnum contesto);

    public DettaglioAnagrafeRestBean populateDettaglioAnagrafeRestBean(Anagrafe rich);

    /**
     * Il metodo aggiorna il campo <b>mail</b> dell'anagrafica
     * 
     * in caso di successo rilancia l'evento
     * 
     * @see EventoEmailAnagrafeAggiornata
     * @param codiceAnagrafe
     * @param email
     */
    public void aggiornaMailEPec(Integer codiceAnagrafe, String email, String pec);

    /**
     * La procedura genera un nuovo codice di verifica mail. Elimina quelle in corso.
     * 
     * @param codiceAnagrafe
     * @param nuovaEmail
     * @return
     */
    public CodiceVerificaMailAnagrafeBean generaCodiceVerificaMail(Integer codiceAnagrafe, String nuovaEmail)
	    throws FunzioneBusinessRemotaException, InvalidConfigurationException;

    /**
     * Aggiorna la mail del richiedente con il codice verifica passato
     * 
     * @param codiceAnagrafe
     * @param codiceVerifica
     * @return
     */
    public EsitoOperazioneAggiornamento updateVerificaMail(Integer codiceAnagrafe, String codiceVerifica);

    public Set<FiltroSoggetti> findSoggettiPersoneCollegate(Integer codice, RicercaAnagraficeCollegateEnum soloIstanzeConAutorizzazioniMercati);
}
