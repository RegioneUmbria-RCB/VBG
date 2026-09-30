package it.gruppoinit.pal.gp.core.features.attivita;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeAttivitaHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IAttivitaDaChiudereHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IAttivitaListHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeHelper;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitaFilter;
import it.gruppoinit.pal.gp.core.features.attivita.datilocalizzativi.LocalizzazioniAttivitaDTO;
import it.gruppoinit.pal.gp.core.features.attivita.restrizioni.RestrizioneAttivitaEsistenteException;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.BaseService;

/**
 * 
 * @author
 */
public interface IAttivitaService extends BaseService<IAttivita, PkId> {

    /**
     * @see IAttivitaDAO#findAll(Integer, Integer)
     */
    public List<IAttivita> findAll(Integer firstResult, Integer maxResult);

    /**
     * Crea una nuova attività a partire dall'istanza passata. Effettua eventuali controlli di esistenza attività
     * 
     * @param istanza
     * @param skipCheckControlloEsistenza
     * @return
     */
    IAttivita creaAttivita(Istanze istanza, boolean skipCheckControlloEsistenza) throws RestrizioneAttivitaEsistenteException;
    /**
     * Crea un'attività a partire da un'istanza.
     * 
     * @param istanza
     */
    //public IAttivita insertCrea(Istanze istanza, boolean skipCheckEsistenza)
    //	    throws IAttivitaEsistentePerDenominazioneException, IAttivitaEsistentePerLocalizzazioneException;
    /**
     * Scollega il CodiceIstanza dall'attività corrente: imposta ISTANZE.FK_ID_I_ATTIV-ITA null;
     * 
     * @param istanza
     * @param attivita
     */
    //public void updateScollega(Istanze istanza, IAttivita attivita);

    /**
     * Collega un'istanza ad una attività
     * 
     * @param istanza
     * @param attivita
     */
    //public void updateCollegaIstanza(Istanze istanza, IAttivita attivita);
    public void collegaIstanza(IAttivita attivita, Istanze istanza);

    /**
     * Elimina l'attività e scollega le istanze ad essa collegate
     */
    public void delete(IAttivita attivita);

    /**
     * @deprecated non implementato, deve essere usato {@link IAttivitaService#crea(Istanze)}
     * @throws new
     *             NotImplementedException
     */
    public void insert(IAttivita entity);

    /**
     * 
     * @param iattivita
     * @param visstorico
     * @return
     */
    public IstanzeHelper populateIstanzeHelper(IAttivita iattivita, boolean visstorico);

    /**
     * 
     * @param filter
     * @return
     */
    public List<IAttivita> findByFilter(IAttivitaFilter filter);
    //public void updateSettaAttiva(IAttivita attivita);
    //    public void updateSettaOperante(IAttivita attivita);

    /**
     * @see IAttivitaDAO#findIstanzeOrdinate(IAttivita iattivita, boolean visstorico)
     */
    public List<Istanze> findIstanzeOrdinate(IAttivita iattivita, boolean visstorico);

    /***
     * Torna gli identificativi numerici delle istanze per l'identificativo attività passato come argomento
     * 
     * @param idiattivita
     * @return
     */
    public List<Integer> findCodiciIstanza(Integer idiattivita);

    public int countIstanzeWithDateValiditaNull(IAttivita iattivita, boolean visstorico);

    /**
     * 
     * <pre>
     * Fa le copie di : 
     * 	1- Soggetti collegati 
     * 	2- Dettaglio informazionie 
     * 	3- Orari apertura 
     * 	4- Mappali
     * 
     * &#64;param istanzaSorgente
     * &#64;param istanzaDestinatario
     * </pre>
     */
    public void insertCopiaTutteLeInfo(Istanze istanzaSorgente, Istanze istanzaDestinatario);

    public List<IAttivita> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult);

    /**
     * <pre>
     * Metodo che invoca WS dell'export della ttivita utilizzando come data quella odierna
     * &#64;param filter 			: filtro per discriminare quali attività esportare
     * &#64;param codicetipoesportazione 	: codice dell'esoportazione da fare
     * &#64;param idComuneTipoesportazione 	: indica il comune su cui fare l'esportazione
     * &#64;param email 			: mail a cui inviare i file di export
     * &#64;param isInvioMail 		: parametro che discrimina se inviare o no la mail
     * &#64;return
     * </pre>
     */
    public byte[] exportIAttivita(IAttivitaFilter filter, Integer codicetipoesportazione, String idComuneTipoesportazione, String email,
	    boolean isInvioMail);

    /**
     * <pre>
     * Metodo che invoca WS dell'export della ttivita utilizzando come data quella passata
     * &#64;param filter 			: filtro per discriminare quali attività esportare
     * &#64;param codicetipoesportazione    :filtro per discriminare quali attività esportare
     * &#64;param idComuneTipoesportazione  :indica il comune su cui fare l'esportazione
     * &#64;param email       		:mail a cui inviare i file di export
     * &#64;param date       		:data in cui fare l'export
     * &#64;param isInvioMail 		:parametro che discrimina se inviare o no la mail
     * &#64;return
     * </pre>
     */
    public byte[] exportIAttivita(IAttivitaFilter filter, Integer codicetipoesportazione, String idComuneTipoesportazione, String email, Date date,
	    boolean isInvioMail);
    /**
     * Trova tutte le istanze che appartengono all'attività ordinate per ISTANZE.DATAVALIDITA DESC, I_ATTIVITAORDINE
     * ASC, CODICEISTANZA DESC.<BR/>
     * L'istanza con la data più recente viene assegnata all'attività e se verticalizzazioni.aggiorna denominazione =
     * true allora viene aggiornata la denominazione dell'attività
     * 
     * @param attivita
     */
    //public void updateSettaUltimaIstanza(IAttivita attivita);

    /**
     * Torna una lista di oggetti IstanzeListHelper filtrando i risultati per l'oggetto IstanzeFilter passato
     * 
     * @param filter
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<IAttivitaListHelper> findIAttivitaListHelperByFilter(IAttivitaFilter filter, Integer firstResult, Integer maxResult);

    /**
     * Torna il conteggio oggetti IstanzeListHelper filtrando i risultati per l'oggetto IstanzeFilter passato
     * 
     * 
     */
    public int countIAttivitaListHelperByFilter(IAttivitaFilter filter);

    /**
     * <pre>
     * Il metodo confronta i campi delle schede dell'attività con i campi presenti sulle schede delle istanze associate
     * all'attività. 1. Se trova una corrispondenza: allora aggiorna il campo con i valori di quello dell'istanza. 2. Se
     * non trova il campo sulla scheda dell'attività alloro lo aggiunge con i valori di quello dell'istanza
     * 
     * La ricerca dei campi dinamici delle schede dell'istanza viene fatta con ordinamento data desc e ordineIstanza
     * asc;viene recuperato solo un record. Questo assicura che il valori che inserire e/o aggiorneremo per quel
     * specifico campo saranno riferiti all'istanza più recente che li contiene.
     * 
     * <b>Se il parametro codiceScheda !=null</b> nel caso non venga trovato nessun valore su istanzedyn2datis un
     * ulteriore logica controlla se tra il modello passato e la scheda dell'attività c'è un campo in comune, in tal
     * caso il campo trovato viene cancellato dalla scheda delle attività
     * 
     * <pre>
     * 
     * @param codiceIAttivita
     */
    public void updateCampiSchedeDinamiche(Integer codiceIAttivita, Integer codiceScheda);

    public boolean updateSnapShot(Integer codiceIstanza);

    /**
     * <pre>
     *     <ol>
     *       <li>Prende la data validità di I_ATTIVITA.CODICEISTANZAULTIMA </li>
     *       <li>Elimina le righe delle tabelle I_ATTIVITA_SNAPSHOT,I_ATTIVITADYN2MOD_T_SNAPSHOT,I_ATTIVITADYN2DATI_SNAPSHOT
     * 	   	riferite a quella data  </li>
     *       <li>Creo un nuovo oggetto della tabella  I_ATTIVITA_SNAPSHOT.IDS e lo inserisco (E' una copia di I_ATTIVITA), imposto
     *     	la data (I_ATTIVITA_SNAPSHOT.DATA) uguale alla dta di validità.</li>
     *       <li>Copia i dati delle tabelle I_ATTIVITADYN2MODELLIT e I_ATTIVITADYN2ATI nelle tabelle I_ATTIVITADYN2MOD_T_SNAPSHOT,
     * 	   	I_ATTIVITADYN2DATI_SNAPSHOT. </li>
     *     </ol>
     * 
     * </pre>
     */
    public IAttivitaSnapshot updateSnapShotCopia(IAttivita attivita);

    /**
     * <pre>
     *    Il metodo effettua la copia delle seguenti informazioni: 
     * 	  <ul>
     *    	<li>I_ATTIVITADYN2MODELLIT -> I_ATTIVITADYN2MOD_T_SNAPSHOT</li>
     *    	<li>I_ATTIVITADYN2DATI -> I_ATTIVITADYN2DATI_SNAPSHOT</li>
     *   </ul>
     *   leggendoli dall'attività e riportandoli nello snapshot più recente ( calcolato prendendo la data di validità dell'istanza che rappresenta
     *   l'attività ). 
     *   I record presenti nello snapshot interessato vengon prima cancellati
     * </pre>
     * 
     * @param attivita
     * @return
     */
    public boolean updateSnapShotCopiaDatiDinamici(IAttivita attivita);

    //FIXME
    /**
     * <pre>
     * 	Crea un nuovo snapshot con:
     *  	1. data = datavalidità dell' istanza passata.
     *          2. istanza = istanza passata
     *          3. altri valori = valori presenti in Iattivita (attivita)
     * 
     * </pre>
     * 
     * @param attivita
     * @param dataSnapshot
     * @return
     */
    public IAttivitaSnapshot updateSnapShotCrea(IAttivita attivita, Istanze istanza);

    /**
     * Ritorna l'attivita associata all'istanza, se l'istanza passata è quella che rappreseta l'attività
     * (codicUltimaistanza==iattivita.codiceultimaistanza).
     * 
     * @param codiceUltimaIstanza
     * @return l'oggetto attivita se esiste, altrimenti NULL
     */
    public IAttivita findByUltimaIstanza(Integer codiceUltimaIstanza);

    /**
     * Ritorna l'attivita associata all'istanza passata.
     * 
     * @param codiceIstanza
     * @return l'oggetto attivita se esiste, altrimenti NULL
     */
    public IAttivita findByIstanze(Integer codiceIstanza);

    public void clear();

    public void flush();

    //public void insertCollegaModellit(Set<AlberoprocD2modtatt> alberoprocD2modtatts, IAttivita iAttivita);
    //   
    /**
     * Recupera le attività filtrate per IAttivitaFilter e aggiunge la scheda passata. La ricerca delle attività verrà
     * paginata; l'inserimento della scheda verrà fatto su un numero limitato di record e poi committati. L'operazione
     * sarà ripetuta per tutte le attivita trovate; Questo per evitare possibili OOM dovuto a liste con un alto numero
     * di record.
     * 
     * @param filter
     * @return: ritorna una stringa contenente le informazioni sulle operazioni svolte (Attivita aggiornate, Errori
     *          riscontrati)
     */
    public String updateAddSchedeDinamiche(IAttivitaFilter filter, Integer codiceScheda);

    /**
     * Recupero tutte le attivita che non hanno uno snapshot
     */
    public List<IAttivita> findAttivitaWithoutSnapshot(Integer firstResult, Integer maxResult);

    public List<Integer> findIdAttivitaWithoutSnapshot(Integer firstResult, Integer maxResult);

    public List<IAttivita> findByIattivitaTipologie(Integer codiceIattivitaTipologia, Integer firstResult, Integer maxResult);
    /**
     * <pre>
     * Il metodo esegue le azioni: 
     * 
     * 		1. Aggiorna il record Iattivita passato 
     * 		2. Ricalcola gli snapshot associati all 'attività a partire dallo snapshot corrispondente alla data passata
     * 		   ( per la logica di creazione degli snapshot, non possono esistere più snapshot alla stessa data,l'eventuale presenza
     *              sarebbe una anomalia).
     * 
     * &#64;param entity
     * &#64;param data
     * &#64;param codiceIstanza : parametro opzionale, nel caso di collegamente di un istanza all'attività se non esiste 
     *                        lo snapshot per la data dell'istanza verrà utilizzato per crearlo.
     * </pre>
     */
    //public void updateAndAggiornaSnapshot(IAttivita entity, Date dataSnapshotPartenza, Integer codiceIstanza);

    /**
     * Il medoto recupera le due istanza e scambia il campo ordine
     * 
     * @param codiceIstanza
     * @param codiceIstanzaSup
     */
    public void updateUpOrdine(Integer codiceIstanza, Integer codiceIstanzaSup);

    /**
     * Il medoto scambia l'ordine tra le due istanze
     * 
     * @param codiceIstanzaPrec
     * @param codiceIstanzaSuc
     */
    public void scambiaOrdine(Integer idAttivita, Integer codiceIstanzaPrec, Integer codiceIstanzaSuc);

    /**
     * torna la lista delle attività esistenti che siano anche attive
     * 
     * @param codiceistanza
     * @param tipoEccezione
     * @return
     */
    public List<IAttivita> findListaAttivitaEsistenti(Integer codiceistanza, String tipoEccezione);

    public void ricalcolaSnapshot(Istanze istanza);

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
    public String exportModalitaPentaho(IAttivitaFilter attivitaFilter, Esportazioni esportazioni, Date data, String emailResponsabile,
	    String contesto, boolean isInviaMail);

    /**
     * Calcola la data di inizio attivita. La data di inizio sarà data da istanze.datavalidita dell'istanza
     * rappresentativa dell'attivita stessa. Se non viene trovata toena null
     * 
     * @param iAttivita
     * @return
     */
    public Date calcoloDataInizioAttivita(IAttivita iAttivita, Date allaData);

    /**
     * Calcola la data di fine attivita. La data di fine attività viene recuperara dal valore del campo dinamico
     * presente nella scheda dell'ultima istanza che rappresenta l'attività. Come ultima si intende l'ultima con
     * datavalidita!=null. Se l'ultima non ha questo campo si cerca risalendo la catena. Il campo della scheda da
     * utilizzare per recuperare la data è configurato nella parametro della verticalizzazione
     * I_ATTIVITA.CAMPO_DYN_FINE_ATT.
     * 
     * @param iAttivita
     * @return
     */
    public Date calcoloDataFineAttivita(IAttivita iAttivita, Date allaData);

    /**
     * aggiorna i campi datainizio e data fine dell'attività passata
     * 
     * @param codiceAttivita
     */
    public IAttivita updateDataInizioEFineAttivita(Integer codiceAttivita);

    public List<IAttivitaDaChiudereHelper> findAttivitaScadute(Date data, Integer firstResult, Integer maxResult);

    //public Integer countAttivitaScadute();
    public int updateNonOperanteENonAttiva(IAttivitaDaChiudereHelper iAttivitaDaChiudereHelper);

    /**
     * <pre>
     *      Devo ciclare tutti gli snapshot ordinati per i_attivita e data asc
     * Solo su idcomune (parametro aggiuntivo idattivita dal quale partire)
     * 
     * Per ogni
     * Setto attiva da questa query
     * 
     * select COUNT(*), AZIONE from istanze where idcomune='E256' AND SOFTWARE='CO' AND FK_IDI_ATTIVITA IS NOT NULL AND FK_IDI_ATTIVITA=534 
     * AND DATAVALIDITA<=TO_DATE('2019-02-15', 'YYYY-MM-DD')
     * AND  AZIONE<>'='
     * GROUP BY AZIONE
     * 
     * Su variabile setto idattività e attiva true o false
     * 
     * Al cambio dell’attività e dopo l’ultima (non ci dimentichiamo) setto anche attiva su attivita salvata su idattività
     * </pre>
     * 
     * @param codiceAttivita
     *            opzionale codice attivita dal quale partire
     */
    public void updateSistemaAttivaSuSnapshot(Integer codiceAttivita);

    /**
     * <pre>
     *      Devo ciclare tutti gli snapshot ordinati per i_attivita e data asc
     * Solo su idcomune (parametro aggiuntivo idattivita dal quale partire)
     * 
     * Per ogni
     * estraggo la denominazione secondo la funzione già presente e faccio l'update
     * 
     * 
     * Su variabile setto idattività 
     * 
     * Al cambio dell’attività e dopo l’ultima (non ci dimentichiamo) setto anche la denominazione su attivita salvata su idattività
     * </pre>
     * 
     * @param codiceAttivita
     *            opzionale codice attivita dal quale partire
     */
    public void updateSistemaDenominazioneAttivita(Integer codiceAttivita);

    /**
     * crea una struttura IstanzeAttivitaHelper, che contiene l'istanza e tutte le autorizzazioni (comprese quelle
     * subentarate)
     * 
     * @param istanzes
     * @return
     */
    public List<IstanzeAttivitaHelper> populateIstanzeAttivitaAutorizzazioniHelper(List<Istanze> istanzes, boolean raggruppaAutPerIstanza);

    public List<IstanzeAttivitaHelper> populateIstanzeAttivitaConcessioniHelper(List<Istanze> istanzes, boolean raggruppaAutPerIstanza);

    public Integer generaCodiceOsservatorio();

    public List<LocalizzazioniAttivitaDTO> findLocalizzazioniByFilter(IAttivitaFilter filter);

    public List<Integer> findIdAttivitaDaiParametriDelCartografico(String uuidChiamata);
}
