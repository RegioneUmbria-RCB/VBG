package it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate;

import it.gruppoinit.pal.gp.core.dao.helper.OrdineEnum;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzecollegate;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzecollegateHelper;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.core.service.exception.OperazioniAutomaticheException;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface IstanzecollegateService extends BaseService<Istanzecollegate, PkId> {

    /**
     * @see IstanzecollegateDAO#findAll(Integer, Integer)
     */
    public List<Istanzecollegate> findAll(Integer firstResult, Integer maxResult);

    public List<Istanzecollegate> findByFilterTable(FilterTable filterTable);

    /**
     * Il metodo effetua una ricerca sulla tabella ISTANZECOLLEGATE filtrando per idcomune e istanza,<br/>
     * ordinando la ricerca rispettivamente per:</br>
     * 1- <b>Progressivo</b> 2- <b>Ordine</b> <br/>
     * Ogni struttura IstanzacollateHelper conterrà :</br>
     * 1- L'istanza passata<br/>
     * 2- Una lista di di istanze collegate con lo stesso campo <b>progressivo</b>
     * 
     * @param istanze
     *            filtro per recuperare solo i record relativi all'istanza scelta
     * @return Una lista di istanze collegate helper
     */
    // public List<IstanzecollegateHelper> findIstanzecollegateByIstanza(Istanze istanze);
    /**
     * @see IstanzecollegateDAO#maxOrdineByProgressivo(Integer progressivo)
     */
    public Integer maxOrdineByProgressivo(Integer progressivo);

    /**
     * @see IstanzecollegateDAO# maxProgressivo(Integer progressivo)
     */
    public Integer maxProgressivo();

    /**
     * Il metodo crea un collegamento tra l'istanza che stiamo configurando e l'istanza passata, secondo la logica:<br/>
     * (Il collegamento è fatto tramite il campo progressivo,istanze collegate avranno lo stesso progressivo)<br/>
     * <ol>
     * <li>Se'istanza passata è già presente nella tabella ISTANZACOLLEGATE <br/>
     * <ul>
     * <li>Per ogni sua occorrenza si inserisce un record nella tabella ISTANZACOLLEGATE con: <br />
     * - progressivo uguale alle occorrenze trovate <br/>
     * - ordine uguale a il max + 1 ordine dei record con progressivo in esame<br />
     * - codiceistanza uguale al codice dell'istanza di partenza (quella che stiamo configurando)</li>
     * </ul>
     * </li>
     * <li>Se L'istanza passata non è presente: <br/>
     * <ul>
     * <li>Inseriamo due record nella tabella ISTANZECOLLEAGATE con: <br/>
     * - Codice progressimo il max per il comune in esame <br/>
     * - Ordine 1 e 2<br/>
     * - Codiceistanza quello di quella in configurazione e quella passata</li></li>
     * </ol>
     * 
     * @param istanzeDacollegare
     */
    public void insertCollegamento(Istanze istanzeDacollegare, Istanze istanze, boolean rilanciaEccezioneSeNonCollegabile);

    /**
     * Ricerca nella tabella istanzecollegate tutti i record che hanno codiceistanza uguale a quello dell'istanza
     * passato
     * 
     * @param istanze
     * @return
     */
    public List<Istanzecollegate> findIstanzeCollegateByIstanza(Istanze istanze);

    /**
     * Ricerca tutti i record con progressivo passato
     * 
     * @param progressivo
     * @return ritorna una lista di oggetti Istanzecollegate con il progressivo uguale a quello passato
     */
    public List<Istanzecollegate> findByProgressivo(Integer progressivo);

    /**
     * Il metodo elimina il collegamento tra due istanze.<br/>
     * Logica:<br/>
     * 
     * <pre>
     * 1. Toglie il record nella tabella ISTANZECOLLEGATE
     * 2. Verifica se ci sono altri record sulla tabella ISTANZECOLLEGATE 
     * 	  con progressivo uguale al record appena tolto
     *     2.1 : se risultato è > 1 proseguo al punto 3.
     *     2.2 : se risultato è == 1 elimino anche questo unico record rimasto
     * 3. Setta a nulle la proprietà istanzaDacollegare dove objToDelete.istanza è 
     *    uguale a quella {@link Istanzecollegate}.istanzaDacollegare dello stesso progressivo
     * </pre>
     * 
     * @param objToDelete
     */
    public void deleteCollegamento(Istanzecollegate objToDelete);

    /**
     * Per ogni collegamento in cui esiste l'istanza passata richiama la funzionalità {@link IstanzecollegateService} .
     * {@link #deleteCollegamento(Istanzecollegate)}
     * 
     * @param istanze
     */
    public void deleteCollegamenti(Istanze istanze);

    /**
     * Ritorna un oggetto istanza collegate filtrata per progressivo e ordine.<br/>
     * 
     * 
     * @param progressivo
     * @param ordine
     */
    public Istanzecollegate findByProgressivoAndOrdine(Integer progressivo, Integer ordine);

    /**
     * Metodo che aggiorna l'ordine di un record:<br/>
     * <br/>
     * 1- se OrdineEnum.UP aumenta di uno quello passato e diminuisce di uno quello di ordine superiore<br/>
     * 2- se OrdineEnum.DOWN diminuisce di uno quello passato e aumenta di uno quello di ordine inferiore<br/>
     * 
     * Se non viene passato OrdineEnum rilancia un eccezione<br/>
     * 
     * @param istanzecollegate
     * @param up
     */
    public void updateOrdine(Istanzecollegate istanzecollegate, OrdineEnum upOrDown);

    /**
     * Metodo che ritorna una lista di istanze collegate filtrate per istanza e progressivo
     * 
     * @param istanze
     * @param progressivo
     * @return
     */
    public List<Istanzecollegate> findByIstanzaAndProgressivo(Istanze istanze, Integer progressivo);

    /**
     * Ritorna un oggetto istanza collegate filtrata per istanza, progressivo e ordine.<br/>
     * Per come sono generati i record anche se l'istanza, il progressivo e l' ordine non sono chiavi l'oggetto deve
     * essere unico (altrimenti anomalia sul DB)
     * 
     * @param progressivo
     * @param ordine
     */
    public Istanzecollegate findByIstanzaAndProgressivoAndOrdine(Istanze istanza, Integer progressivo, Integer ordine);

    /**
     * Ricerca nella tabella istanzecollegate tutti i record che hanno codiceistanzacoolegata uguale a quello
     * dell'istanza passato
     * 
     * @param istanze
     * @return
     */
    public List<Istanzecollegate> findIstanzeCollegateByIstanzaCollegata(Istanze istanze);

    /**
     * @see IstanzecollegateDAO#getSchemaPrecedentiAndSuccessive(Istanze istanza)
     */
    public IstanzecollegateHelper getSchemaPrecedentiAndSuccessive(Istanze istanza);

    /**
     * metodo per la procedura di import
     */
    public void clear();

    public void insertCollegamentoPrecedente(Istanze istanzeDacollegare, Istanze istanze, boolean rilanciaEccezioneSeNonCollegabile);

    /**
     * <pre>
     * Permette di collegare all'istanza passata un istanza precedente  che non appartenga a nessuna catena secondo la logica:
     * 		1- CASO 1: l'istanza di partenza ha un solo collegamento (appartiene a una sola catena): recupero il record di ISTANZECOLLEGATE che ha
     * 			   codiceistanza il codice dell'istanza passata e codiceistanzacollegata =null, 
     * 			   aggiorno il record mettentdo il codice dell'istanza passata sul campo codiceistanzacollegata e il codice dell'istanza da collegare
     *                     sul campo codice istanza. Inserisco un nuovo record in ISTANZECOLLEGATE con codiceistaza uguale al codice dell'istanza 
     *                     da collegare e codiceistanzacollegata=NULL.
     *          2- CASO 2: l'istanza di partenza appartiene a due o più catene : Creo una nuova catena (max progressivo + 1) con due record su
     *                     ISTANZECOLLEGATE 
     *                     record 1: codiceistanza uguale al codice dell'istanza da collegare e codiceistanza da collegare uguale a NULL 
     *                     record 2: codiceistanza uguale al codice dell'istanza passata e codiceistanzacollegata quello dell'istanza da collegare          
     * 		
     * Il metodo non permette di fare questo collegamento quando l'istanza da collegare e l'istanza passata hanno entrambi uno o più collegamenti.
     * 
     * 		
     * 		
     * 			
     * &#64;param istanzeDacollegare
     * &#64;param istanze
     * &#64;param isCollegaAttivita : true la collega all'attività dell'istanza principale, false non la collega all'attivita della principale
     * </pre>
     */
    public void insertCollegamento(Istanze istanzeDacollegare, Istanze istanzaPrincipale, boolean rilanciaEccezioneSeNonCollegabile,
	    boolean isCollegaAttivita);

    /**
     * torna il numero dei record per i quali è vero che CODICEISTANZA=codiceIstanza or
     * CODICEISTANZACOLLEGATA=codiceIstanza
     * 
     * @param codiceIstanza
     * @return
     */
    public int countIstanzeCollegateByIstanza(Integer codiceIstanza);

    /**
     * Richiama N volte il metodo insertCollegamentoPrecedente(....) per ogni istanza passata
     * 
     * @param lista_istanze_da_collegare
     * @param istanze
     * @param b
     */
    public void insertCollegamentoMultiplo(String lista_istanze_da_collegare, Istanze istanze, boolean rilanciaEccezioneSeNonCollegabile);

    /**
     * Richiama N volte il metodo insertCollegamentoPrecedente(....) per ogni istanza passata
     * 
     * @param lista_istanze_da_collegare
     * @param istanze
     * @param b
     */
    public void insertCollegamentoPrecedenteMultiplo(String lista_istanze_da_collegare, Istanze istanze, boolean b);

    Boolean isExistIstanzeCollegateByIstanza(Integer codiceIstanze);

    boolean isExistIstanzeCollegateByIstanza(String[] codiciIstanze);

    /**
     * Al collegamento delle istanze verifica la configurazione della tabella alberoproc_movimenti configurata nella
     * voce di albero dell'istanza di origine. Se trovata allora inserisce nell'istanza di destinazione il movimento/i
     * trovato/i
     * 
     * @param codiceIstanzaOrigine
     * @param codiceIstanzaDestinazione
     */
    public void insertMovimentoInIstanzaCollegata(Integer codiceIstanzaOrigine, Integer codiceIstanzaDestinazione);

    /**
     * Al collegamento delle istanze verifica la configurazione della tabella alberoproc_movimenti configurata nella
     * voce di albero dell'istanza di origine. Se trovata allora inserisce nell'istanza di destinazione il movimento/i
     * trovato/i
     * 
     * @param codiceIstanzaOrigine
     * @param codiceIstanzaDestinazione
     */
    public void deleteMovimentoInIstanzaCollegata(Integer codiceIstanzaOrigine, Integer codiceIstanzaDestinazione)
	    throws OperazioniAutomaticheException;

    /**
     * <pre>
     * 1 - Ciclo la lista dei record vwistanzacollegata recuperate 
     * 2 - Per ognuna recupero tutti i record in istanzacollegate che hanno lo stesso progressivo dell'istanzacollegata passata. 
     * 3 - Creo l'oggetto IstanzacollegataHelper inserendo l'istanza di partenza e la lista delle  delle istanze collegate trovate al passo 2
     * 4 - Aggiungo l'oggetto creato alla list di istanzecollegateHelper
     * </pre>
     * 
     * @param codiceIstanza
     * @return
     */
    public List<IstanzecollegateHelper> findIstanzecollegateByIstanzaPerVisualizzazione(Integer codiceIstanza);
}
