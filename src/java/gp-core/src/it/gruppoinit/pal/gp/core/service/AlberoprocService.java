package it.gruppoinit.pal.gp.core.service;

import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ConfigurazionePreferenzeUsoPerMercatoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoli;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocCommand;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InterventoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InterventoSimpleBean;

public interface AlberoprocService extends BaseService<Alberoproc, PkId> {

    /**
     * Metodo per la ricerca di una Lista di alberoproc. Viene filtrato utilizzando dei criteri passati come parametro.
     * 
     * @param criteria
     * @return
     */
    public List<Alberoproc> findByCriteria(DetachedCriteria criteria);

    /**
     * Recupera una tipoprocedura di avvio a partire dal codice di un procedimento
     * 
     * @param codiceProcedimento
     * @return
     */
    public Tipiprocedure getProcedura(Integer codiceProcedimento);

    /**
     * Recupera il movimento di default
     * 
     * @param codiceProcedimento
     * @return
     */
    public Tipimovimento getMovimentoDefault(Integer codiceProcedimento);

    /**
     * Metodo che ricerca l'Alberoproc per scCodice.
     * 
     * @param sccodice
     * @return
     */
    public Alberoproc findByScCodice(String sccodice);

    public Alberoproc findBySoftwareAndScCodice(String software, String sccodice);

    /**
     * Ricerca il procedimento per id, filtrando per il software corrente.<br />
     * se hideDisabled=true allora aggiunge la condizione scAttivo = true
     * 
     * @param id
     * @return
     */
    public Alberoproc findByIdAndCurrentSoftware(Integer id, Boolean hideDisabled);

    /**
     * Metodo per il salvataggio dei ruoli associati a una voce dell'alberoproc
     * 
     * @param alberoproc
     * @param alberoprocRuolis
     */
    public void saveRuoli(Alberoproc alberoproc, Set<AlberoprocRuoli> alberoprocRuolis);

    /**
     * Torna la lista di dati della tabella alberoproc organizzata a gerarchia padre/figli
     * 
     * 
     * @return
     */
    public List<AlberoprocCommand> findAlberoprocHierarchy(Integer rootCodiceAlbero);
    
    /**
     * Torna la lista di dati della tabella alberoproc organizzata a gerarchia padre/figli senza cache
     * 
     * 
     * @return
     */
    public List<AlberoprocCommand> findAlberoprocHierarchyNoCache(Integer rootCodiceAlbero);

    /**
     * Metodo che restituisce la struttura dell'albero a partire dalla foglia con questo SC_CODICE=scCodice
     * 
     * @param scCodice
     * @return
     */
    public AlberoprocCommand findAlberoprocFigli(String scCodice);

    /**
     * Metodo che restituisce la lista di alberoproc che sono root per il software corrente
     * 
     * @return
     */
    public List<Alberoproc> findRootsAlberoProc();

    /**
     * @see AlberoprocDAO#findDescrizionePrimaVoceAlberoproc(String scCodice)
     */
    public String findDescrizionePrimaVoceAlberoproc(String scCodice);

    /**
     * Metodo per l'inserimento di un alberoproc. Prima di inserire l'alberoproc viene determinato il SC_CODICE
     * disponibile.
     * 
     * @param alberoproc
     * @param alberoprocPadre
     */
    public void insertAlberoproc(Alberoproc alberoproc, Alberoproc alberoprocPadre);

    public String findProgressivo(Alberoproc alberoproc);

    /**
     * Aggiorna il campo progressivo istanze. Se la voce dell'albero passata come argomento non contiene il progressivo
     * ripercorre a ritroso l'albero dei procedimenti fino a trovare la prima voce che contiene un progressivo e
     * aggiorna quella voce (in quanto il progressivo è stato presumibilmente preso da quella voce)
     * 
     * @param alberoproc
     * @param progressivo
     */
    public void updateProgressivo(Alberoproc alberoproc, String progressivo);

    /**
     * 
     * @param alberoproc
     * @param propertyName
     * @return
     */
    public Object findParametroprotocollo(Alberoproc alberoproc, String propertyName, String codiceComune);

    /**
     * Effettua una findById in base all'id passato e torna una struttura AlberoprocHelper richiamando il metodo
     * findAlberoprocHelper(Alberoproc alberoproc)
     * 
     * @param idAlberoProc
     * @return
     */
    public AlberoprocHelper findAlberoprocHelper(Integer idAlberoProc);

    /**
     * A partire da un oggetto Alberoproc popola, risalendo la gerarchia della voce dell'albero, le seguenti liste ed
     * oggetti:
     * <ul>
     * <li>Liste:
     * <ul>
     * <li>Set&lt;AlberoprocDocumenti&gt; alberoprocDocumentis</li>
     * <li>Set&lt;AlberoprocLeggi&gt; alberoprocLeggis</li>
     * <li>Set&lt;AlberoprocEndo&gt; alberoprocEndos</li>
     * <li>Set&lt;AlberoprocDyn2modellit&gt; alberoprocDyn2modellits</li>
     * <li>Set&lt;AlberoprocRuoli&gt; alberoprocRuolis</li>
     * <li>Set&lt;AlberoprocOneri&gt; alberoprocOneris</li>
     * <li>Set&lt;AlberoprocAteco&gt; alberoprocAtecos</li>
     * </ul>
     * </li>
     * <li>Oggetti:
     * <ul>
     * <li>Azioni azione</li>
     * <li>String progressivoistanze</li>
     * <li>Responsabili responsabile (responsabile procedimento):<br />
     * Risale le voci dell'albero e se non trova niente prende il valore dalla CONFIGURAZIONE.CODICERESPONSABILE del
     * software dell'albero</li>
     * <li>Responsabili respistruttoria (responsabile istruttoria)</li>
     * <li>Responsabili operatoreStc (Operatore default per inserimento da STC)</li>
     * <li>Tipiprocedure tipoProcedura</li>
     * </ul>
     * </li>
     * </ul>
     * 
     * @param alberoproc
     * @return AlberoprocHelper
     */
    public AlberoprocHelper findAlberoprocHelper(Alberoproc alberoproc);

    public void updateAlberoprocCache();

    /**
     * metodo per verificare se un nodo è disabilitato. il nodo è disabilitato se ha flag scAttivo = 1 o appartiene ad
     * un sottoalbero con almeno un nodo padre disabilitato
     * 
     * @param scCodice
     * @return
     */
    public boolean findSeDisabilitato(String scCodice);

    /**
     * Trova tutti i figli di un alberoproc che parte con un determinato scCodice (scCodicePadre). se solo primo
     * livello==true allora torna solamente la lista del livello immediatamente sotto all'alberoproc padre
     * 
     * @param scCodicePadre
     * @param soloPrimoLivello
     * @param tipoOrdinamento
     * @param isPerCalcoloprogressivo
     *            se true allora l'ordinamento verrà fatto solamente su sc_codice altrimenti su sc_ordine,
     *            sc_descrizione, sc_codice
     * @return
     */
    public List<Alberoproc> findAlberoprocFigli(String scCodicePadre, boolean soloPrimoLivello, DAOOrderTypeEnum tipoOrdinamento,
	    boolean isPerCalcoloprogressivo);

    /**
     * Torna un scCodice successivo a quello passato se scCodicePrecedente = 020101 torna 020102
     * 
     * @param scCodicePrecedente
     * @return
     */
    public String calcolaProssimoCodice(String scCodicePrecedente);

    /**
     * La funzione torna un oggetto azione a partire da una voce di albero scelta ed una lista di endoprocedimenti
     * passati. Se lista di endo procedimenti non è vuota allora cerca l'endo principale per quella voce di albero e ne
     * prende l'azione. Se non c'è un endo principale cicla tra quelli e se ne trova uno prende in ordine azione (+) poi
     * (-). Se non ce ne sono altri prende l'azione di alberoproc risalendo la gerarchia. Nel caso non venga trovata
     * l'azione viene restituita una di default con valore (=).
     * 
     * @param alberoproc
     * @param codiciInventarioList
     * @return
     */
    public Azioni findAzioniDaEndoOAlberoproc(Alberoproc alberoproc, List<Integer> codiciInventarioList);

    /**
     * La funzionalità sposta una voce dell'albero da una posizione indicata dal parametro sorgente ad un altra indicata
     * dal parametro destinazione. Rilancia BusinessValidationException se non è possibile spostare le voci dell'albero
     * selezionato
     * 
     * @param sorgente
     * @param destinazione
     */
    public void spostaVoceAlbero(Integer sorgente, Integer destinazione);

    /**
     * @see {@link BaseDAO#flush()}
     */
    public void flush();

    /**
     * @see {@link BaseDAO#clear()}
     */
    public void clear();

    /**
     * Trova la lista delle voci di ALBEROPROC legate ad un responsabile (ALBEROPROC.CODICERESPONSABILE)
     * CODICEOPERATORE_STC CODICERESPISTRUTTORIA
     * 
     * @param responsabileprocedimento
     * @return
     */
    public List<Alberoproc> findByResponsabileprocedimento(Responsabili responsabileprocedimento, Integer firstResult, Integer maxResult);

    /**
     * Trova la lista delle voci di ALBEROPROC legate ad un responsabile dell'istruttoria
     * (ALBEROPROC.CODICERESPISTRUTTORIA)
     * 
     * @param responsabileprocedimento
     * @return
     */
    public List<Alberoproc> findByResponsabileistruttoria(Responsabili responsabileistruttoria, Integer firstResult, Integer maxResult);

    /**
     * Trova la lista delle voci di ALBEROPROC legate ad un operatore STC (ALBEROPROC.CODICEOPERATORE_STC)
     * 
     * @param responsabileprocedimento
     * @return
     */
    public List<Alberoproc> findByOperatoreSTC(Responsabili operatoreSTC, Integer firstResult, Integer maxResult);

    /**
     * Trova la lista delle voci di ALBEROPROC legate ad un operatore STC (ALBEROPROC.FKIDPROCEDURA)
     * 
     * @param codice
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Alberoproc> findByTipiprocedure(Integer codice, Integer firstResult, Integer maxResult);

    /**
     * Recupera il modello di riepilogo per l'area riservata
     * 
     * @param id
     * @return
     */
    public AlberoprocDocumenti findModelloDomandaFO(Integer id);

    /**
     * Esegue un'update del campo sc_codice per l'alberoproc identificato da codiceAlberoproc
     * 
     * @param codiceAlberoProc
     * @param scCodice
     */
    public void updateScCodice(Integer codiceAlberoproc, String scCodice);

    /**
     * Il metodo controlla se la voce dell'albero in esame gestisce un mercato e un particolare uso, il metodo tonerà :
     * 1- True : vere entrambe le condizioni 2- False : se almeno una delle due non vera
     */
    public ConfigurazionePreferenzeUsoPerMercatoEnum isGestisceMercatoAndUso(Integer codiceAlberoproc);

    /**
     * metodo per il recupero della testata del workflow dell'areariservata associato alla voce dell'albero. il metodo
     * risale l'albero fino a quando non trova un flusso. Se non trova il flusso ritorna null.
     * 
     * @param codice
     * @return
     */
    public FoArjStepsTestata findFoArjStepsTestata(Integer codice);

    /**
     * Il metodo controlla se è possibile pubblicare manualmente la voce dell'albero come intervento CART.<br />
     * E' pubblicabile se:
     * <ul>
     * <li>È una foglia dell'albero e non una cartella</li>
     * <li>Il diretto genitore abbia un record collegato in STP_ENDO_TIPO2, dal quale recuperare il 47.100R</li>
     * <li>Se ha un record collegato in STP_ENDO_TIPO2 non deve avere il campo CODICE_TIPOLOGIA_ENDO valorizzato (
     * l'aggiornamento del dizionario prevede la valorizzazione di quel campo)</li>
     * </ul>
     * 
     * @param codiceAlberoproc
     * @return
     */
    public boolean checkSePubblicabileSuCART(Integer codiceAlberoproc);

    public void updatePubblicaSuCart(Integer codiceAlberoproc, boolean pubblica);

    public List<InterventoSimpleBean> findListaInterventiSottonodiDi(Integer codiceAlberoproc);

    public List<Integer> findGerarchiaNodiPadre(Integer codiceAlberoproc, boolean ancheLeVociDisabilitateoNonPubblicate);

    public List<Integer> findGerarchiaNodiPadreInversa(Integer codiceAlberoproc, boolean ancheLeVociDisabilitateoNonPubblicate);

    public InterventoBean findInterventoBean(Integer codiceAlberoproc);

    /**
     * 
     * <pre>
     *      	dove tipoRicerca può valere:
     * 		    - tutteParole: Cerca le voci che contengono tutte le parole
     * 		    - interaFrase: Cerca le voci che contengono l'intera frase
     * 		    - almenoUnaParola: Cerca le voci che contengono almeno una parola
     * 	
     * 		    e campiRicerca può valere:
     * 		    - titoli: cerca nei titoli
     * 		    - titoliDescrizioni: cerca nei titoli e nelle descrizioni
     * </pre>
     * 
     * @param testoDaCercare
     * @param tipoRicerca
     * @param campiRicerca
     * @return
     */
    public List<InterventoSimpleBean> findInterventiByDescrizione(String testoDaCercare, String tipoRicerca, String campiRicerca, Integer firstResult,
	    Integer maxResults);

    /**
     * <pre>
     * Il metodo ritorna una stringa che rappresenta la descrizione a partire da un intervento risalendo per N livelli.
     * ES. COMMERCIO(100) --> AREA PUBBLICA(101) --> VICINATO(102) --> AVVIO (103)
     * 
     * Se 
     * codiceIntervento= 103 
     * livello =2 
     * risultato : VICINATO - AVVIO
     * 
     * &#64;param codiceInteventoPartenza
     * &#64;param livello
     * &#64;return
     * 
     * </pre>
     */
    public String findDescrizioneInterventoFromLivello(Integer codiceInteventoPartenza, Integer livello);

    public List<InterventoSimpleBean> findGerarchiaDettaglioNodiPadre(Integer codiceIntervento, boolean ancheLeVociDisabilitateoNonPubblicate);

    public String findGerarchiaAlberoGruppi(Integer idAlberoproc, Integer codice);

    /**
     * <pre>
     * A partire dal scCodice ritorna una lista di AlberoprocHelper gerarchicamente superiori contenete i campi :
     * 	1. Alberoproc currentAlberoproc;
     * 	2. List alberoprocDyn2modellits
     * 
     * La lista è ordinata a partire da quello gerarchicamente più alto.
     * 
     * Gli altri campi e liste non sono popolate per non appesantire la ricerca 
     * &#64;param scCodiceInterveto
     * &#64;return
     * </pre>
     */
    public List<AlberoprocHelper> findModelliTIstanzaEreditati(String scCodiceInterveto);

    public int countByAmministrazioni(Integer codiceAmministrazione);

    /**
     * recupera l'alberatura che l'operatore, in base ai ruoli può vedere. Vengono recuperate le voci padre e figli per
     * le quali l'operatore ha dei ruoli in RESPONSABILIRUOLI==ALBEROPROC_RUOLI
     * 
     * @param codiceResponsabile
     * @return
     */
    public List<AlberoprocCommand> findAlberoprocHierarchyPerRuoli(Integer codiceResponsabile);    
    
}
