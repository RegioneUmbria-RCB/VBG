package it.gruppoinit.pal.gp.core.service;

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
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocCommand;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InterventoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InterventoSimpleBean;

import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;

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
     * Metodo che ricerca l'Alberoproc per scCodice.
     * 
     * @param sccodice
     * @return
     */
    public Alberoproc findByScCodice(String idcomune, String sccodice);

    /**
     * Ricerca il procedimento per id, filtrando per il software corrente.<br />
     * se hideDisabled=true allora aggiunge la condizione scAttivo = true
     * 
     * @param id
     * @return
     */
    public Alberoproc findByIdAndCurrentSoftware(String idcomune, Integer id, Boolean hideDisabled);

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
    public List<AlberoprocCommand> findAlberoprocHierarchy(String idcomune, Integer rootCodiceAlbero);

    /**
     * Metodo che restituisce la struttura dell'albero a partire dalla foglia con questo SC_CODICE=scCodice
     * 
     * @param scCodice
     * @return
     */
    public AlberoprocCommand findAlberoprocFigli(String idcomune, String scCodice);

    /**
     * Metodo che restituisce la lista di alberoproc che sono root per il software corrente
     * 
     * @return
     */
    public List<Alberoproc> findRootsAlberoProc();

    /**
     * @see AlberoprocDAO#findDescrizionePrimaVoceAlberoproc(String scCodice)
     */
    public String findDescrizionePrimaVoceAlberoproc(String idcomune, String scCodice);

    /**
     * Metodo per l'inserimento di un alberoproc. Prima di inserire l'alberoproc viene determinato il SC_CODICE
     * disponibile.
     * 
     * @param alberoproc
     * @param alberoprocPadre
     */
    public void insertAlberoproc(Alberoproc alberoproc, Alberoproc alberoprocPadre, StpEndoTipo2 stpEndoTipo2);

    public void updateAlberoproc(Alberoproc alberoproc, StpEndoTipo2 stpEndoTipo2);

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
     * A partire da un oggetto Alberoproc popola, risalendo la gerarchia della voce dell'albero, le seguenti liste ed
     * oggetti:
     * <ul>
     * <li>Liste:
     * <ul>
     * <li>
     * Set&lt;AlberoprocDocumenti&gt; alberoprocDocumentis</li>
     * <li>
     * Set&lt;AlberoprocLeggi&gt; alberoprocLeggis</li>
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
     * <li>
     * Responsabili responsabile (responsabile procedimento):<br />
     * Risale le voci dell'albero e se non trova niente prende il valore dalla CONFIGURAZIONE.CODICERESPONSABILE del
     * software dell'albero</li>
     * <li>
     * Responsabili respistruttoria (responsabile istruttoria)</li>
     * <li>Responsabili operatoreStc (Operatore default per inserimento da STC)</li>
     * <li>Tipiprocedure tipoProcedura</li>
     * </ul>
     * </li>
     * </ul>
     * 
     * @param alberoproc
     * @return AlberoprocHelper
     */
    public AlberoprocHelper findAlberoprocHelper(Alberoproc alberoproc, String codiceComuneDiCompetenza);

    public void updateAlberoprocCache();

    /**
     * metodo per verificare se un nodo è disabilitato. il nodo è disabilitato se ha flag scAttivo = 1 o appartiene ad
     * un sottoalbero con almeno un nodo padre disabilitato
     * 
     * @param scCodice
     * @return
     */
    public boolean findSeDisabilitato(String idcomune, String scCodice);

    /**
     * Trova tutti i figli di un alberoproc che parte con un determinato scCodice (scCodicePadre). se solo primo
     * livello==true allora torna solamente la lista del livello immediatamente sotto all'alberoproc padre
     * 
     * @param scCodicePadre
     * @param soloPrimoLivello
     * @param tipoOrdinamento
     * @return
     */
    public List<Alberoproc> findAlberoprocFigli(String idcomune, String scCodicePadre, boolean soloPrimoLivello, DAOOrderTypeEnum tipoOrdinamento,
	    Boolean isPerCalcoloprogressivo);

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
    public void spostaVoceAlbero(String idcomune, Integer sorgente, Integer destinazione);

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
    public AlberoprocDocumenti findModelloDomandaFO(String idcomune, Integer id);

    /**
     * Esegue un'update del campo sc_codice per l'alberoproc identificato da codiceAlberoproc
     * 
     * @param codiceAlberoProc
     * @param scCodice
     */
    public void updateScCodice(String idcomune, Integer codiceAlberoproc, String scCodice);

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
    public FoArjStepsTestata findFoArjStepsTestata(String idcomune, Integer codice);

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
    public boolean checkSePubblicabileSuCART(String idcomune, Integer codiceAlberoproc);

    public void updatePubblicaSuCart(String idcomune, Integer codiceAlberoproc, boolean pubblica);

    public List<InterventoSimpleBean> findListaInterventiSottonodiDi(String idcomune, Integer codiceAlberoproc, boolean soloModulisticaNazionale,
	    boolean isAreaRiservata, boolean isUtenteTester, String codiceComune);

    public List<Integer> findGerarchiaNodiPadre(String idcomune, Integer codiceAlberoproc, boolean soloModulisticaNazionale, boolean isAreaRiservata,
	    boolean isUtenteTester, String codiceComune);

    public List<Integer> findGerarchiaNodiPadreInversa(String idcomune, Integer codiceAlberoproc, boolean soloModulisticaNazionale);

    public InterventoBean findInterventoBean(String idcomune, Integer codiceAlberoproc, String codiceComune, boolean isAreaRiservata,
	    boolean isUtenteTester);

    /**
     * 
     <pre>
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
    public List<InterventoSimpleBean> findInterventiByDescrizione(String idcomune, String testoDaCercare, String tipoRicerca, String campiRicerca,
	    Integer firstResult, Integer maxResults, boolean filtraSoloComunica, boolean isAreaRiservata, boolean isUtenteTester, String codiceComune);

    public List<InterventoSimpleBean> findInterventiByDescrizioneNew(String idcomune, String testoDaCercare, String tipoRicerca, String campiRicerca,
	    Integer firstResult, Integer maxResults, boolean filtraSoloComunica, boolean soloModulisticaNazionale, boolean isAreaRiservata,
	    boolean isUtenteTester, String codiceComune);

    public String findDescrizioneAlberoproc(String idcomune, Integer codiceAlberoproc);

    public List<AlberoprocCommand> findAlberoprocCommand(String idcomunebase, Integer rootCodiceAlbero);
}
