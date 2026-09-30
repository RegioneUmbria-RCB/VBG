package it.gruppoinit.pal.gp.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.dao.IAttivitaSnapshotDAO;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IASnapshotViewerHelper;
import it.gruppoinit.pal.gp.core.domain.helper.WsExportBean;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitaFilter;

/**
 * 
 * @author gianpaolot
 */
public interface IAttivitaSnapshotService extends BaseService<IAttivitaSnapshot, PkId> {

    /**
     * @see IAttivitaSnapshotDAO#findAll(Integer, Integer)
     */
    public List<IAttivitaSnapshot> findAll(Integer firstResult, Integer maxResult);

    /**
     * Recupera i record riferiti per l'attivita passata e per quella data
     * 
     * @param dataValidita
     * @return
     */
    public List<IAttivitaSnapshot> findByAttivitaAndData(Integer codiceAttivita, Date dataValidita);

    /**
     * @see IAttivitaSnapshotDAO#findBeforeData(Integer codiceAttivita, Date dataValidita)
     */
    public IAttivitaSnapshot findBeforeData(Integer codiceAttivita, Date dataValidita);

    /**
     * @see IAttivitaSnapshotDAO#findAfterData(Integer codiceAttivita, Date dataValidita)
     */
    public List<IAttivitaSnapshot> findAfterData(Integer codiceAttivita, Date dataValidita);

    /**
     * @see IAttivitaSnapshotDAO#findBeforeDataAndOrdine(Integer codiceAttivita, Date dataValidita, Integer ordine)
     */
    public IAttivitaSnapshot findBeforeDataAndOrdine(Integer codiceAttivita, Date dataValidita, Integer ordine);

    /**
     * Aggiorna il valore del campo "attiva" del record IattivitaSnapShot
     * 
     * @param attivita
     */
    public void updateSettaAttiva(List<Istanze> istanzes, IAttivitaSnapshot attivitaSnapshot);

    /**
     * @see IAttivitaSnapshotDAO#findByAttivita(Integer codiceAttivita)
     */
    public List<IAttivitaSnapshot> findByAttivita(Integer codiceAttivita);

    /**
     * Recupera i record riferiti per l'attivita passata e per codice istanza
     * 
     * @param dataValidita
     * @return
     */
    public List<IAttivitaSnapshot> findByAttivitaAndIstanza(Integer codice, Integer codiceIstanza);

    public void deleteByIstanza(Integer codiceIstanzaScollegata);

    /**
     * Esegue tutta la rutine di snapshot in base ai parametri passati
     * 
     * @param isScollega
     * @param codiceIstanza
     */
    public void updateRutineSnapShot(boolean isScollega, Integer codiceIstanza);

    /**
     * Recupera tutte le attività che non hanno uno snapshot (sulla tabella I_ATTIVITA_SNAPSHOT non c'è un riferimento
     * all' attività) e lo calcola
     */
    public String updateElaboraSnapshotAttivita();

    public IASnapshotViewerHelper populateSnapshotViewerHelper(Integer codiceAttivita);

    public List<IAttivitaSnapshot> findByIattivitaTipologie(Integer codiceIattivitaTipologia, Integer firstResult, Integer maxResult);

    /**
     * <pre>
     * Controlla se una delle info della testata dell'attivita sono cambiate:
     * 
     * 		1. denominazione
     * 		2. attiva
     * 		3. operante
     * 		4. codiceistanza ultima
     * 
     * se almeno uno è cambiato esegue iattivitaSnapshotService.updateSnapshot(codiceistanza ultima)
     * 
     * &#64;param infoTestaAttivitaPrecedente : oggetto Iattivita prima di qualsiasi evento di update (Es. cambio denominazione,cambio cod. istanza ultima,...)
     * &#64;param infoTestaAttivitaAttuale    : oggetto Iativita dopo evento di update(vedi Es. sopra)
     * &#64;return true :  se uno dei dati elencati è variato, altrimenti false
     * </pre>
     */
    //    public boolean isChangedTestataAttivita(IAttivita infoTestaAttivitaPrecedente, IAttivita infoTestaAttivitaAttuale);
    //
    //    public boolean isChangedTestataAttivita(IAttivitaHelper infoTestaAttivitaPrecedente, IAttivita infoTestaAttivitaAttuale);
    public IAttivitaSnapshot findByData(IAttivita attivita, Date date);

    /**
     * Controlla se esistono snapshot per l'attivita passata
     * 
     * @param codiceattivita
     * @return
     */
    public boolean isSnapshotExsistByAttivita(Integer codiceattivita);

    /**
     * <pre>
     * Ricalcola le schede e i campi dinamici per tutti gli snapshot dell'attivita a partire dalla data passata.
     * Nel caso in cui nell'ultimo snapshot (quello che coincide con la situazione attuale di iAttivita) cambiano
     * i valori delle schede e/o dei campi dinamici verrà aggiornato anche lo stato attuale delle schede e/o campi dinamici
     * dell'attività
     * &#64;param iAttivitaSnapshot
     * &#64;param data
     * </pre>
     */
    public void updateDatiDinamiciSnapshots(IAttivita iAttivita, Date data);

    /**
     * <pre>
     * Aggiorna i dati dinamici dello snapshot passato a partire dal precedente se esiste.
     *   1a. Snapshot precedente esiste
     *      1a.1 : Crea copia dati dinamici e schede snapshot precedente
     *   1b. Snapshot precedente non esiste
     *      1b.1 : Crea copia dati dinamici e schede dalla situazione attutale dell'attività annullando i valori
     *   2. Trova tutte le istanze dell'attivita con datavalidità > snapshot precedente e  datavalidità<= snapshot passato
     *      ordinate per data validità asc e istanze.iattivitaordine desc
     *   3. Per ogni istanza trovata:
     *      3.1 verifica se i dati dinamici sono stati modificati (IAttivitaSnapshotService.checkupdateDatiDinamici(....))
     *          3.1.1: si : il metodo ritornerà true
     *          3.1.2: no : non cambia il valore di ritorno del metodo
     *   4. Update campi e schede dinamiche
     * &#64;param iAttivitaSnapshot
     * &#64;return true : se i dati dinamici sono realmente cambiati altrimenti false
     * </pre>
     */
    public boolean updateDatiDinamiciSingoloSnapshot(IAttivitaSnapshot iAttivitaSnapshot);

    /**
     * @see IAttivitaSnapshotDAO#findSnapshotPrecedente(IAttivitaSnapshot iAttivitaSnapshot)
     */
    public IAttivitaSnapshot findSnapshotPrecedente(IAttivitaSnapshot iAttivitaSnapshot);

    /**
     * @see IAttivitaSnapshotDAO#findFromData(Integer codiceAttivita, Date dataValidita)
     */
    public List<IAttivitaSnapshot> findFromData(Integer codiceAttivita, Date dataValidita);

    /**
     * Ricalcolare la testa e i dati dinamici di tutti gli snapshot per l'attivita a partire dalla data passata
     * 
     * @param iAttivita
     * @param dataPartenza
     */
    public void updateRicalcolaSnapshot(IAttivita iAttivita, Date dataPartenza);

    /**
     * Ricalcola i valori ddella testata dello snapshot passato, attualmente viene ricalcolato solo l'istanza
     * rappresentativa (la denominazione potrebbe essere stata modificata manualamente,attiva e operanta non cambiano)
     * 
     * @param iAttivita
     * @param data
     * @return
     */
    public boolean updateDatiTestaSingoloSnapshot(IAttivita iAttivita, Date data);

    /**
     * elabora lo snapshot di una attività (sulla tabella I_ATTIVITA_SNAPSHOT non c'è un riferimento all' attività) e lo
     * calcola
     */
    public void updateElaboraSnapshotAttivita(Integer codiceIattivita);

    public List<WsExportBean> findIAttivitaSnapshotByFilter(IAttivitaFilter filter, Date dataEsportazione);

    public boolean existAfterData(Integer codiceAttivita, Date datavalidita);

    public List<Integer> findTuttiICodici(Integer codiceAttivitaDaCuiPartire);

    public Map<Date, Integer> findSnapshots(Integer idAttivita, Date dataRicalcolo);
}
