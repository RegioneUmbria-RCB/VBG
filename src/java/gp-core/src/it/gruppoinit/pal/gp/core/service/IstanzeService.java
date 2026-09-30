package it.gruppoinit.pal.gp.core.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDaChiudereHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerInterventiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerProcedimentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.SorteggidettaglioDTO;
import it.gruppoinit.pal.gp.core.domain.web.CambioInterventoCommand;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeOnLineHelper;
import it.gruppoinit.pal.gp.core.domain.web.IstanzePerAttivitaFilter;
import it.gruppoinit.pal.gp.core.domain.web.TracciatoEquitaliaFilter;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.HelperTypeEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.custom.DettaglioRigaIstanze;
import it.gruppoinit.pal.gp.core.features.istanze.rest.AggiornaRiferimentiProtocolloIstanzaRequest;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.AggiornamentoProtocolloException;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.OperazioniAutomaticheException;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniAccessiHelper;
import it.gruppoinit.pal.gp.core.service.helper.ResponsabiliAssegnazioniHelper;
import it.gruppoinit.pal.gp.core.service.helper.TempisticaIstanzaHelper;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;

public interface IstanzeService extends BaseService<Istanze, PkId> {

    public void clear();

    public static enum TipoInserimento {

	/**
	 * 
	 */
	PROTOCOLLAZIONE_PARAMETRI_NON_PROTOCOLLARE(0),
	/**
	 * 
	 */
	PROTOCOLLAZIONE_PARAMETRI_INSERIMENTO_NORMALE(1),
	/**
	  * 
	  */
	PROTOCOLLAZIONE_PARAMETRI_DA_ONLINE(2),
	/**
	  * 
	  */
	PROTOCOLLAZIONE_PARAMETRI_INSERIMENTO_RAPIDO(4),
	/**
		 * 
		 */
	PROTOCOLLAZIONE_PARAMETRI_CONTROLLA_RAMI_PADRE(8),
	/**
	 * 
	 */
	PROTOCOLLAZIONE_PARAMETRI_ISTANZA_MOVIMENTI_AUTORIZZAZIONE_BACKOFFICE(16);

	private Integer value;

	private TipoInserimento(Integer v) {

	    value = v;
	}

	public Integer value() {

	    return value;
	}

	public static TipoInserimento fromValue(Integer v) {

	    if (null == v) {
		throw new IllegalArgumentException("Non è stato trovato un valore per il tipoinserimento null");
	    }
	    TipoInserimento[] values = TipoInserimento.values();
	    for (TipoInserimento tipoInserimento : values) {
		if (v.equals(tipoInserimento.value())) {
		    return tipoInserimento;
		}
	    }
	    throw new NotImplementedException("Non è stato trovato un valore per il tipoinserimento [" + String.valueOf(v) + "]");
	}
    }

    /**
     * Trova tutte le istanze di un determinato modulo software
     */
    @Override
    public List<Istanze> findAll(Integer firstResult, Integer maxResult);

    /**
     * Metodo per ricercare tra le istanze. La stringa passata viene valuta come numeroistanza o descrizione del
     * richiedente
     * 
     * @param textToSearch
     * @return
     */
    public List<Istanze> findByNumeroistanzaOrRichiedente(String filterString);

    /**
     * Metodo per ricercare tra le istanze. La stringa passata viene valutata come numeroistanza, N.protocollo,
     * descrizione o CF del richiedente, descrizione o CF/PI dell'azienda o per indirizzo PEC dell'istanza, del
     * richiedente o dell'azienda.
     * 
     * @param textToSearch
     * @return
     */
    public List<Istanze> findByProtocolloNumeroRichiedenteAziendaOrPEC(String filterString, boolean protocolloInMovimenti);

    /**
     * metodo per recuperare la lista delle istanze della graduatoria per le quali il movimento con tipoMovimento
     * specificato non è stato eseguito. la lista è ulteriormente filtrata in base al soggettoMovimento
     * 
     * @param codiceGraduatoria
     * @param tipoMovimento
     * @param soggettoMovimento
     * @return
     */
    public List<Istanze> findIstanzeDaGraduatoria(Integer codiceGraduatoria, String tipoMovimento, String soggettoMovimento);

    public List<Istanze> findByFilterTable(FilterTable filterTable);

    public List<Istanze> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult);

    /**
     * @param filter
     * @return
     */
    public List<Istanze> findByFilter(IstanzeFilter filter);

    /**
     * il metodo ritorna il tipoAccesso=<b>CONSENTITO</b> se:
     * <ol>
     * <li>L'operatore è amministratore o amministratoresoftware</li>
     * <li>L'operatore ha record su permistanze per l'istanza selezionata</li>
     * <li>L'operatore appartiene a dei ruoli presenti su istanzeruoli per l'istanza selezionata</li>
     * </ol>
     * 
     * il metodo ritorna:
     * <ul>
     * <li>tipoAccesso=<b>SOLA_LETTURA</b> se:
     * <ol>
     * <li>L'operatore ha il flag readOnly=true</li>
     * <li>L'operatore ha un ruolo con flag readOnly=true e flagGestmovimenti=false e flagDisgestmovamm=false</li>
     * </ol>
     * </li>
     * <li>TipoAccesso=<b>SOLA_LETTURA_TUTTI_MOVIMENTI</b> se l'operatore ha un ruolo con flag readOnly=true e
     * flagGestmovimenti=true e flagDisgestmovamm=false</li>
     * <li>TipoAccesso=<b>SOLA_LETTURA_MOVIMENTI_AMM_INTERNA</b> l'operatore ha un ruolo con flag readOnly=true e
     * flagGestmovimenti=true e flagDisgestmovamm=true</li>
     * </ul>
     * 
     * il metodo ritorna il tipoAccesso=<b>NON_CONSENTITO</b> se:
     * <ul>
     * <li>le verifiche precedenti sono false e non ci sono record su istanzeruoli</li>
     * </ul>
     * 
     * @param istanza
     * @param responsabile
     * @return
     */
    public TipoAccessoEnum checkAccessoIstanza(Istanze istanza, Responsabili responsabile);

    /**
     * ritorna il progressivo da utilizzare per l'istanza corrente o "" se la numerazione automatica non è
     * configurata.<br />
     * Il parametro alberoprocCodice è l'id dell'intervento di ALBEROPROC, se è nullo allora non è stato ancora scelto
     * 
     * @param alberoprocCodice
     * @param scriviSubito
     *            se true allora contestualmente alla lettura chiama la funzione
     *            {@link #scriviProgressivo(String, Integer, String)}
     * @return
     */
    public String findProgressivoIstanza(Integer alberoprocCodice, boolean scriviSubito);

    /**
     * 'La funzione verifica se l'operatore è in grado oppure no di modificare l'intervento della pratica.<br />
     * 'L'intervento è modificabile solo se:
     * 
     * <pre>
     *       - Se la verticalizzazione {@link WebConstants#VERTICALIZZAZIONE_MODIFICA_INTERVENTO} è attiva è sempre modificabile.
     *       altrimenti è modificabile solamente se:
     *           - Non esistono altri movimenti ad eccezione di quello di avvio  
     *           - Non esistono oneri collegati alla pratica  
     *           - Non esistono endoprocedimenti attivati  
     *           - Non esistono documenti allegati  
     *           - Non esistono dati dinamici valorizzati
     * </pre>
     * 
     * @param istanza
     * @return
     */
    public String checkModificaIntervento(Istanze istanza);

    /**
     * inserisce un permesso nella tabella permistanze per l'istanza passata come argomento. Il metodo controlla se il
     * permesso per ilresponsabile è già presente ed in questo caso non effettua operazioni sulla base dati
     * 
     * @param entity
     * @param responsabile
     */
    public void inserisciPermessoIstanza(Istanze entity, Responsabili responsabile);

    /**
     * inserisce un ruolo nella tabella istanzeruoli per l'istanza passata come argomento. Il metodo controlla se il
     * ruolo è già presente ed in questo caso non effettua operazioni sulla base dati
     * 
     * @param entity
     * @param ruolo
     */
    public void inserisciRuoloIstanza(Istanze entity, Ruoli ruolo);

    /**
     * @deprecated Per questioni legate alla protocollazione delegata ad un sistema esterno per inserire una pratica
     *             deve essere usato {@link #insert(Istanze, TipoInserimento)}. Infatti il componente di protocollazione
     *             non accede ad oggi alla transazione corrente ed è necessario committare la transazione perché questo
     *             legga i dati scritti nella base dati
     * 
     * @param token
     * @param entity
     * @param tipoInserimento
     */
    public void insert(Istanze entity);

    public void insert(Istanze entity, TipoInserimento tipoInserimento, IPostIstanzeInsertCallBack postCallBack);

    // modificato 2019-01-23 BOCCI/TODINI ripristinato il comportamento di far calcolare la data con la data di sistema
    // La modifica era stata introdotta per la chiusura automatica delle istanze tramite JOB schedulato
    // ma ha comportato problemi nella gestione manuale. il Calcolo della data fine effettiva per la chiusura automatica è
    // stato spostato nel manager che chude le istanze
    public Movimenti insertMovimentoChiusura(Integer codice, boolean isEsitoPositivo);

    /**
     * 
     */
    @Override
    public void delete(Istanze entity);

    /**
     * Trova l'oggetto per la protocollazione
     * 
     * @param entity
     * @return
     */
    public Mailtipo findProtocolloOggetto(Istanze entity);

    /**
     * Trova l'oggetto per la protocollazione
     * 
     * @param entity
     * @return
     */
    public String findFascicoloOggetto(Istanze entity);

    /**
     * 
     * @param entity
     * @param tipoInserimento
     */
    public void operazioniAutomatiche(Istanze entity, TipoInserimento tipoInserimento, AlberoprocHelper helper,
	    IPostIstanzeInsertCallBack postCallBack) throws OperazioniAutomaticheException;

    /**
     * Per ogni oggetto di tipo anagrafe collegato all'istanza (richiedente, intermediario e soggetti collegati) esegue
     * un check sul codice fiscale della scheda anagrafica. Il controllo verifica che il CodiceFiscale sia di 16
     * caratteri e che la penultima lettera del codicefiscale sia una cifra numerica
     * 
     * @param entity
     * @throws OperazioniAutomaticheException
     */
    public void insertEventiCheckCodiceFiscale(Istanze entity) throws OperazioniAutomaticheException;

    /**
     * 
     */
    @Override
    public void update(Istanze entity);

    /**
     * Esegue l'elaborazione dell'istanza
     * 
     * @param istanza
     *            l'istanza da elaborare
     * @param forzaElaborazionePerIstanzeChiuse
     *            se true allora l'elaborazione sarà effettuata anche per i movimenti di un istanza chiusa precedenti a
     *            quello del movimento di chiusura istanza
     */
    public void elabora(Integer codiceIstanza, boolean forzaElaborazionePerIstanzeChiuse, Date elaboraAttivitaDopoData);

    /**
     * Esegue l'elaborazione dell'istanza
     * 
     * @param istanza
     *            l'istanza da elaborare
     * @param forzaElaborazionePerIstanzeChiuse
     *            se true allora l'elaborazione sarà effettuata anche per i movimenti di un istanza chiusa precedenti a
     *            quello del movimento di chiusura istanza
     */
    public void elabora(Integer codiceIstanza, boolean forzaElaborazionePerIstanzeChiuse);

    /**
     * Esegue l'elaborazione dell'istanza
     * 
     * @param istanza
     */
    public void primaElaborazione(Istanze istanza);

    /**
     * Calcola la durata del procedimento dell'istanza tenendo conto di eventuali CDS, SOSPENZIONI, INTERRUZIONI o
     * PROROGHE
     * 
     * @param istanza
     * @return I giorni di durata del procedimento
     */
    public int calcolaDurataProcedimento(Istanze istanza);

    /**
     * a seconda della procedura scelta per l'istanza la validità si calcola secondo i seguenti criteri:
     * <ul>
     * <li>MA: data del movimento di avvio configurato nella procedura</li>
     * <li>DP: durata del procedimento (data di inizio istanza configurato nella procedura ( determinazione inizio
     * istanza )+ gg durata della procedura)</li>
     * <li>MS: data del movimento specificato e in relazione all'esito (0: qualsiasi esito, 1: esito negativo, 2:esito
     * positivo)</li>
     * <li>NC: non calcolare la data di validità</li>
     * </ul>
     * 
     * @param istanza
     */
    public void calcolaDataValidita(Integer codiceIstanza);

    /**
     * Calcola la data di inizio istanza
     * 
     * @param istanza
     * @return
     */
    public Date calcolaDataInizioIstanza(Istanze istanza);

    /**
     * Torna il movimento dell'ultima trasmissione effettuata per una istanza. se non sono state fatte trasmissioni o
     * non sono state fatte tutte torna null
     * 
     * @param istanza
     * @return
     */
    public Movimenti dataUltimaTrasmissione(Istanze istanza);

    /**
     * La funzione torna un messaggio se non è possibile cancellare l'istanza altrimenti stringa vuota
     * 
     * @param istanza
     * @return
     */
    public String checkDeleteIstanza(Istanze istanza);

    /**
     * La funzione torna se è possibile modificare i dati dell'istanza
     * 
     * @param istanza
     * @return
     */
    public boolean checkModificaIstanza(Istanze istanza);

    /**
     * Popola un oggetto {@link IstanzeOnLineHelper} che contiene la lista delle istanze provenienti da vari
     * frontoffice. Le domande vengono prese da DOMANDESTC
     * 
     * @return
     */
    public IstanzeOnLineHelper findIstanzeOnline();

    /**
     * 
     * Il metodo Scorre tutti i record presenti in movimenti_tempistica effetta i calcoli e fissa i valori nella tabella
     * istanze_tempistica.
     * 
     * @param entity
     */
    public void calcolaTempisticaIstanza(Istanze entity);

    /**
     * Aggiorna la proprietà chiusura con il nuovo stato
     * 
     * @param istanza
     * @param nuovoStato
     */
    public MovimentiHelper updateStatoIstanza(Istanze istanza, String nuovoStato);

    /**
     * Aggiorna la proprietà codicecomune con il nuovo valore
     * 
     * @param codiceIstanza
     * @param codiceComune
     */
    public void updateComuneIstanza(Integer codiceIstanza, String codiceComune);

    /**
     * Aggiorna la proprietà lavoriestesa con il nuovo valore
     * 
     * @param codiceIstanza
     * @param lavoriestesa
     */
    public void updateLavoriestesa(Integer codiceIstanza, String lavoriestesa);

    /**
     * Aggiorna la proprietà tipoProtFallita con il nuovo valore
     * 
     * @param codiceIstanza
     * @param tipoProtFallita
     */
    public void updateTipoProtFallita(Integer codiceIstanza, String tipoProtFallita);

    /**
     * Aggiorna la proprietà numeroistanza con il nuovo valore
     * 
     * @param codiceIstanza
     * @param numeroistanza
     */
    public void updateNumeroistanza(Integer codiceIstanza, String numeroistanza);

    /**
     * Aggiorna la proprietà metriquadrati con il valore passato
     * 
     * @param istanza
     * @param metriquadrati
     */
    public void updateMqIstanza(Integer codiceIstanza, BigDecimal metriquadrati);

    /**
     * <pre>
     * 
     * Il metodo recupera una lista di istanze, che saranno utilizzate per la creazione di un attivita (I_ATTIVITA).
     * Le istanze sanno filtrate per :
     * 
     *  1- Azione (opzionale) deve essere selzionato il tipo raggruppamento azione e scelta un 
     *     azione tra quelle della tabella di base.
     *  2- Comune
     *  3- Software
     *  4- attivita (IAttivita) deve essere null
     * 
     * &#64;param istanzePerAttivitaFilter
     * &#64;return Lista di istanze
     * 
     * </pre>
     */
    public List<Istanze> findByIstanzePerAttivitaFilter(IstanzePerAttivitaFilter istanzePerAttivitaFilter);

    /**
     * <pre>
     * Ritorna il numero di record presenti nella tabella filtrata per l'oggetto filter passato (IstanzeFilter)
     * 
     * &#64;param filter
     * &#64;return
     * </pre>
     */
    public int countByFilter(IstanzeFilter filter);

    /**
     * <pre>
     * Ritorna il numero di record presenti nella tabella filtrata per l'oggetto filter passato (FilterTable)
     * 
     * &#64;param filter
     * &#64;return
     * </pre>
     */
    public int countByFilterTable(FilterTable filter);

    /**
     * <pre>
     * Ritorna una lista di oggetti istanze filtrata per l'oggetto filter passato (IstanzeFilter) della 
     * 
     * 	1- dimensione 			:	maxResult
     *  2- a partire dalla posizione	:	firstResult
     * 
     * &#64;param filter
     * &#64;return
     * </pre>
     */
    public List<Istanze> findByFilter(IstanzeFilter filter, Integer firstResult, Integer maxResult);

    /**
     * Torna una lista di oggetti IstanzeListHelper filtrando i risultati per l'oggetto IstanzeFilter passato
     * 
     * @param filter
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<IstanzeListHelper> findIstanzeListHelperByFilter(IstanzeFilter filter, Integer firstResult, Integer maxResult);

    /**
     * Torna il conteggio oggetti IstanzeListHelper filtrando i risultati per l'oggetto IstanzeFilter passato
     * 
     * 
     */
    public int countIstanzeListHelperByFilter(IstanzeFilter filter);

    /**
     * @see IstanzeDAO#findIstanzePerInserimentoMassivo(IstanzeFilter)
     * 
     */
    public List<Istanze> findIstanzePerInserimentoMassivo(IstanzeFilter filter);

    /**
     * Torna la lista delle istanze che sono state replicate per una istanza.
     * 
     * @param istanza
     * @return
     */
    public List<Istanze> visualizzaRepliche(Istanze istanza);

    /**
     * Controlla se l'istanza ha generato delle repliche o è stata replicata. <br />
     * Torna l'istanza che ha generato le repliche o null se non ci sono repliche.
     * 
     * @param istanza
     *            da controllare
     * @return
     */
    public Istanze isReplicata(Istanze istanza);

    /**
     * Clona i dati di una pratica preparandola per un'operazione di creaReplica
     * 
     * @param istanza
     * @return
     */
    public Istanze createTemplateFromIstanzaForReplica(Istanze istanza);

    /**
     * Trova tutti i codici istanza di un software e idcomune ed intervento (Opzionale).
     * 
     * @param psoftware
     * @param List
     *            &lt;Integer&gt; codiceIntervento (Può essere nullo)
     * 
     * @return
     */
    public List<Integer> findTuttiCodiciIstanzaPerSoftwareAndIntervento(String psoftware, List<Integer> codiceIntervento);

    /**
     * Il metodo scrive un progressivo a partire dai dati passati.
     * 
     * @param numeroIstanza
     *            Il numero che rappresenta il progressivo da scrivere
     * @param alberoProcCodice
     *            Il codice dell'intervento preso dall'istanza cui appartiene il numeroistanza
     * @param codiceSoftware
     *            Il codice delmodulo software preso dall'istanza cui appartiene il numeroistanza
     * @param eseguiCommitImmediata
     *            se true esegue un flush ed una commit immediata
     */
    public void scriviProgressivo(String numeroIstanza, Integer alberoProcCodice, String codiceSoftware, boolean eseguiCommitImmediata);

    /**
     * Torna la lista delle Istanze di un Richiedente
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Istanze> findByAnagrafeRichiedente(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle Istanze di un Professionista
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Istanze> findByAnagrafeProfessionista(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle Istanze di un Titolarelegale
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Istanze> findByAnagrafeTitolarelegale(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle Istanze di un Alberoproc
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Istanze> findByAlberoproc(Integer codiceAlberoproc, Integer firstResult, Integer maxResult);

    public int countByAlberoproc(Integer codiceAlberoproc);

    /**
     * <pre>
     * Aggiorna la data iniziale delle istanze legate alla procedura passata e in base al campo determinazioneinizioistanza 
     * (Determinazione della data di inizio istanza) 
     * &#64;param tipiprocedure
     * </pre>
     */
    public void updateDataInizioIstanzaPerProcedura(Tipiprocedure tipiprocedure, Integer limiteIstanzeAggiornabiliPerCiclo);

    /**
     * Recupera le istanze collegate alla tipo procedura passata. Se la procedura appartiene al software TT la ricerca
     * verra effettuata su tutte le istanze altrimenti solo per il software della procedura.
     * 
     * @param tipiprocedure
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Istanze> findByProcedure(Tipiprocedure tipiprocedure, Integer firstResult, Integer maxResult);

    /**
     * Ritorna il numero di istanze associate alla procedura passata. Se la procedura appartiene al software TT la
     * ricerca verra effettuata su tutte le istanze altrimenti solo per il software della procedura.
     * 
     * @param tipiprocedure
     * @return
     */
    public int countByProcedure(Tipiprocedure tipiprocedure);

    /**
     * <pre>
     * Aggiorna la data di validità delle istanze legate alla procedura passata e in base al campo determinazioneefficacia 
     * (Determinazione della data di inizio istanza) 
     * &#64;param tipiprocedure
     * </pre>
     */
    public void updateDataValiditaIstanzaPerProcedura(Tipiprocedure tipiprocedure, Integer limiteIstanzeAggiornabiliPerCiclo);
    /**
     * Aggiorna il campi ordineAttivita per ogni istanza passata, e setta come istanza che rappresenta l'attivita quella
     * in prima posizione (prima quella con data di validità maggiore,a parita di data quella con ordine minore
     * [1,2,3,...,n] )
     * 
     * @param ordineIstanze
     * @param codiceIstanze
     */
    //public void updateAttivitaOrdine(String ordineIstanze, String codiceIstanze);

    /**
     * Ritorna la lista delle istanze per cui l'anagrafe storico è professionista o richiedente o titolare legale
     * 
     * @param anagrafeStorico
     * @return
     */
    public List<Istanze> findProfessionistaOrRichiedenteOrTitLegaleStorico(Anagrafestorico anagrafeStorico);

    /**
     * Il metodo fa una copia delle schede e deti dati dinamici dell'istanza sorgente (Istanzedyn2modellit e
     * IstanzeDyn2dati) su un istanza destinataria passata
     * 
     * @param istanzaSorgente
     * @param istanzaDestinatario
     */
    public void updateCopiaSchedeIstanza(Istanze istanzaSorgente, Istanze istanzaDestinatario);

    /**
     * @see IstanzeDAO#findByAttivitaAndBeforeDataValiditaIstanza(Integer codice, Date dataValidita,Integer ordine)
     * 
     */
    public List<Istanze> findByAttivitaAndBeforeDataValiditaIstanza(Integer codiceAttivita, Date dataValidita, Integer ordine);

    /**
     * <pre>
     * Calcola a partire da una lista se un attivita è attiva o no. Secondo la condizione se la somma delle "azioni +" è
     * maggiore della somma delle "azioni -" delle istanze passate allora ritorna "true" altrimenti ritorna "false". Il
     * metodo ha senso e ritorna un valore logicamente corretto se le istanze appartengo alla stessa attivita. In caso
     * contrario verrà rilanciato un errore.
     * 
     * &#64;param istanzes
     * &#64;return ritorna valore boolean: true : attiva; false: non attiva
     * </pre>
     */
    public boolean findIfIsAttivitaAttivaFromIstanze(List<Istanze> istanzes);

    /**
     * ritorna la lista delle istanze apparteneti all'attività
     * 
     * @param codice
     * @return
     */
    public List<Istanze> findByAttivita(Integer codice);

    /**
     * <pre>
     * Ritorna tutte le istanze :
     *  	1. associate all'attivita passata
     *  	2. datavalidita > fromDate 
     *  	3. datavalidita <= toDate
     * &#64;param codiceattivita
     * &#64;param fromDate opzionale (può essere null)
     * &#64;param toDate
     * &#64;return Lista di oggetti istanze
     * </pre>
     */
    public List<Istanze> findByAttivitaAndIntervalloDataValidita(Integer codiceattivita, Date fromDate, Date toDate);

    /**
     * @see IstanzeDAO#findByAttivitaAndBeforeDataValiditaIstanza(Integer codiceAttivita, Date dataValidita, Integer
     *      ordine, boolean includiDataValiditaNull)
     * 
     */
    public List<Istanze> findByAttivitaAndBeforeDataValiditaIstanza(Integer codiceAttivita, Date dataValidita, boolean includiDataValiditaNull);

    /**
     * <pre>
     * Ritorna tutte le istanze :
     *  	1. associate all'attivita passata
     *  	2. datavalidita = dataValidita
     *  	
     * &#64;param codiceattivita
     * &#64;param dataValidita
     * 
     * &#64;return Lista di oggetti istanze
     * </pre>
     */
    public List<Istanze> findByAttivitaAndDataValidita(Integer codiceattivita, Date dataValidita);

    /**
     * @see IstanzeDAO#findIstanzaUltimaAttivitaAllaData(Integer codiceAttivita, Date dataValidita)
     * 
     */
    public Istanze findIstanzaUltimaAttivitaAllaData(Integer codiceAttivita, Date dataValidita);

    /**
     * <pre>
     * Ricalcola l'ordine di tutte le istanze associate all'attività passata, con data di validità uguale a quella dell'istanza passata.
     * &#64;param istanza
     * &#64;param attivita
     * </pre>
     */
    public void updateCalcoloIattivitaOrdine(Istanze istanza, IAttivita attivita);

    /**
     * @see IstanzeDAO#findOrdineAttivitaMaxByData(IAttivita attivita, Date datavalidita)
     * 
     */
    public int findOrdineAttivitaMaxByData(IAttivita attivita, Date datavalidita);

    /**
     * carica nel command le informazioni dell'istanza corrente e quelle derivanti dal nuovo intervento scelto
     * 
     * @param istanza
     * @param cambioInterventoCommand
     */
    public void populateCambioInterventoCommand(Istanze istanza, CambioInterventoCommand cambioInterventoCommand);

    /**
     * carica nel command le informazioni dell'istanza corrente e quelle derivanti dal nuovo intervento scelto
     * 
     * @param istanza
     * @param cambioInterventoCommand
     */
    public void updateCambioInterventoIstanza(Istanze istanza, CambioInterventoCommand cambioInterventoCommand);

    public List<Istanze> findByTipoSoggetto(Integer codiceTiposoggetto, int firstResult, int maxResults);

    public List<Istanze> findByTipologiaIstanza(Integer codiceTipologia, int firstResult, int maxResults);

    public List<Istanze> findByTipimovimentoAvvio(String tipomovimento, int firstResult, int maxResults);

    public List<Istanze> findByTipiarchivioistanze(Integer codiceTipoarchivio, int firstResult, int maxResults);

    public List<Istanze> findByImpianti(Integer codiceImpianto, int firstResult, int maxResults);

    public List<Istanze> findByAree2(Integer codiceArea2, int firstResult, int maxResults);

    /**
     * @see IstanzeDAO# findAttivitaOrdineByAttivitaAndDatavalidita(Integer codiceAttivita, Date dataValidita)
     * 
     */
    public List<IstanzeDTO> findAttivitaOrdineByAttivitaAndDatavalidita(Integer codiceAttivita, Date dataValidita);

    public void updateOrdineAttivita(Integer codIstanza, Integer nuovoOrdine);

    /**
     * Recupero l'istanza che ha generato l'attività con data validita valorizzata.
     * 
     * @return
     */
    public Istanze findIstanzaOrigineAttivita(Integer codiceAttivita);

    public void evict(Istanze entity);

    /**
     * esegue l'export delle istanze in base hai filtri passati, crea una file che potà essere anche inviato per email
     * 
     * @param filter
     * @param codiceEsp
     * @param idComuneEsportazione
     * @param emailResponsabile
     * @param isInviaMail
     * @return
     */
    public byte[] export(IstanzeFilter filter, Integer codiceEsp, String idComuneEsportazione, String emailResponsabile, boolean isInviaMail);

    /**
     * esegue l'export tramite il servizio pentaho delle istanze in base hai filtri passati, crea una file che potrà
     * essere anche inviato per email
     * 
     * @param filter
     * @param codiceEsp
     * @param idComuneEsportazione
     * @param emailResponsabile
     * @param isInviaMail
     * @return
     */
    public String exportModalitaPentaho(IstanzeFilter filter, Esportazioni esportazioni, String emailResponsabile, boolean isInviaMail);

    public List<IstanzeDaChiudereHelper> findIstanzedaChiudere();

    public List<IstanzePerInterventiHelper> countNumeroIstanzeGrupByInterventi(String codiceSoftware, Date fromDate, Date toDate, Integer startRow,
	    Integer maxRow);

    public List<IstanzePerProcedimentiHelper> countNumeroIstanzeGrupByProcedimenti(String codiceSoftware, Date fromDate, Date toDate,
	    Integer startRow, Integer maxRow);

    /**
     * Ritorna il numero della pratiche per cui non è stata assegnato un responsabile dell'istruttoria. Le istanze oltre
     * a non avere il responsabile dell'istruttoria, devono avere popolato il campo "gruppoistruttori", da cui si potra
     * scegliere l'istruttore. La lista sarà filtrata per il responsabile procedimento con l'utente loggato
     * 
     * @param responsabiliProcedimento
     * @return
     */
    public int countIstanzeDaAssegnareAdIstruttoreByRespProcedimento(Responsabili responsabile);

    /**
     * Ritorna il numero della pratiche per cui l'utente loggato è stato assegnato come resp istruttoria temporaneo. Le
     * istanze oltre ad avere popolato il responsabile dell'istruttoria temporaneo, devono avere popolato il campo
     * "gruppoistruttori". La lista sarà filtrata per il responsabile istruttoria temporaneo l'utente loggato
     * 
     * @param responsabiliProcedimento
     * @return
     */
    public int countIstanzeAssegnareAdIstruttoreByRespProcedimento(Responsabili currentlyAuthenticatedUserDetails);

    public void accettaOrRifiutaRuoloIstruttore(Integer codiceIstanza, Responsabili istruttore, Boolean accetta, Boolean rigetta);

    public void sendEmailNoticheFunzionalitaAntiCorruzione(Istanze istanza, Responsabili istruttore, boolean accetta, boolean rigetta,
	    boolean assegnazione) throws FunzioneBusinessRemotaException;

    /**
     * Aggiorna l'istruttore per l'istanza passata,
     * 
     * @param istanza
     */
    public void updateIstruttore(Integer codiceIstanza, Integer codiceIstruttore, boolean tracciaInEventi);

    public Istanze findByUiid(String uuid);

    public SorteggidettaglioDTO findByIstanza(Integer codiceIstanza);

    public ResponsabiliAssegnazioniHelper calcolaAssegnazioneResponsabiliProc(Integer codiceIstanza);

    public ResponsabiliAssegnazioniHelper calcolaAssegnazioneIstruttori(Integer codiceIstanza);

    public Set<Integer> findDocumentiIstanzaByMetadati(Integer codiceistanza, List<CodiceDescrizioneBean> mds);

    public void updateContatori(boolean processaSoloIlPrimoGiornoDEllanno);

    public boolean verificaResponsabileNellIstanze(Integer codiceResponsabile);

    public void updatePrendiInCarico(Integer codiceIstanza, Integer codice);

    /**
     * Ritorna il numero della pratiche il cui stato abbia la colonna FLAG_WARNING impostata a 1
     * 
     * @param responsabiliProcedimento
     * @return
     */
    public int countIstanzeConStatoInWarning();

    public int countByAmministrazioni(Integer codice);

    public String findEmailSoggettoPratica(Integer codiceAnagrafe, Integer codiceIstanza, Integer codiceMovimento);

    public boolean isDataSuccessivaAllaChiusura(Integer codiceIstanza, Date data);

    public void updateStatoIstanze(Set<Integer> istanzes, String codicestato);

    /**
     * @see IstanzeDAO# findIstanzaLocalizzazioneSimile(Integer codiceStradario, String civico, String esponente, String
     *      colore, Integer codiceIstanza, Integer firstResult, Integer maxResult)
     * 
     */
    public List<IstanzeListHelper> findIstanzaLocalizzazioneSimile(Integer codiceStradario, String civico, String esponente, String colore,
	    Integer codiceIstanza, Integer firstResult, Integer maxResult);

    public boolean findIfIsAttivitaAttivaFromDataValuditaAndAttivita(Date data, Integer codice);

    /**
     * Ritorna un helper per la visualizzazione delle informazioni di una autorizzazione legata ai Accessi
     * 
     * @param numeroAutorizzazione
     * @param dataAutorizzazione
     * @return
     */
    public AutorizzazioniAccessiHelper findAutorizzazioniAccessiHelper(String numeroAutorizzazione, Date dataAutorizzazione, String cfImpresa,
	    String pivaImpresa) throws BusinessValidationException;

    /**
     * Ritorna un helper per la visualizzazione delle informazioni di una autorizzazione legata ai Accessi
     * 
     * @param idAutorizzazione
     * @return
     */
    public AutorizzazioniAccessiHelper findAutorizzazioniAccessiHelper(Integer idAutorizzazione) throws BusinessValidationException;

    public List<Integer> findIstanzaPerTracciatoEquitalia(TracciatoEquitaliaFilter tracciatoEquitaliaFilter, boolean isInviate, Integer firstResult,
	    Integer maxResults);

    /**
     * Ricerca il numero della pratica "padre". È l'istanza che ha generato l'istanza del codice passato come argomento.
     * Questa ricerca è possibile solamente per pratiche create da STC e da nodi Interni ovvero
     * Verticalizzazioniparametri.STC.NLA_IDNODO.
     * 
     * Se non trovata torna null
     * 
     * 
     * @param codiceIstanza
     * @return
     */
    public String findNumeroIstanzaPadre(Integer codiceIstanza);

    public DatiProtocolloResponseType insertProtocolloEfascicolo(Istanze entity, TipoInserimento tipoInserimento);

    /**
     * @see IstanzeDAO#findIstanzaProtocollazioneFallitaByTipoProtocollazione(String[] codicetipoprotocollazione)
     * 
     */
    public List<Integer> findIstanzaProtocollazioneFallitaByTipoProtocollazione(String[] codicetipoprotocollazione);

    /**
     * Il metodo collega una istanza ad una attività aggiornando solo i riferimenti dell'attività ( id e ordine default
     * ) nell'istanza
     * 
     * @param attivita
     * @param istanza
     */
    public void collegaIstanzaAdAttivita(IAttivita attivita, Istanze istanza);

    /**
     * 
     * @param istanza
     */
    void eseguiFormuleDelleSchedeDinamiche(Istanze istanza) throws OperazioniAutomaticheException;

    public void insert(Istanze entity, TipoInserimento tipoInserimento);

    public void updateRiferimentiProtocollo(AggiornaRiferimentiProtocolloIstanzaRequest request) throws AggiornamentoProtocolloException;

    /**
     * Il metodo ritorna i cf del richiedente/tecnico dell'istanza (solo se persone fisiche)
     * 
     * @param codiceIstanza
     * @return
     */
    public Set<String> getCfRichiedentiPrincipaliIstanza(Integer codiceIstanza);

    /**
     * Ritorna le informazioni di tempistica istanza
     * 
     * @param codiceIstanza
     * @return
     */
    public TempisticaIstanzaHelper tempisticaDettaglio(Integer codiceIstanza);

    public Set<String> getCfRichiedentiPrincipaliIstanzaAut(Integer codiceIstanza);

    public boolean isChiusa(Integer codiceIstanza);

    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeResponsabiliPerIstanzeInCorso(Integer codiceIstanza, Map<Integer, Integer> codResp,
	    String scCodice);

    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeIstruttoriPerIstanzeInCorso(Integer codiceIstanza, Map<Integer, Integer> codResp,
	    String scCodice);

    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeResponsabiliPerIstanzeInCorsoNew(Integer codiceIstanza,
	    Map<Integer, Integer> codResp, String scCodice, Integer idTestata);

    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeIstruttoriPerIstanzeInCorsoNew(Integer codiceIstanza, Map<Integer, Integer> codResp,
	    String scCodice, Integer idTestata);

    public boolean existsById(Integer codiceIstanza);

    List<DettaglioRigaIstanze> findIstanzeListHelperByFilterMass(IstanzeFilter filter, HelperTypeEnum type, Integer firstResult, Integer maxResult);

    public boolean aggiornaLocalizzazionePrimariaDaCartografico(Integer codiceIstanza);
}