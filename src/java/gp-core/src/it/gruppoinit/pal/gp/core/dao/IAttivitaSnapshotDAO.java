package it.gruppoinit.pal.gp.core.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.WsExportBean;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitaFilter;

/**
 * 
 * @author gianpaolot
 */
public interface IAttivitaSnapshotDAO extends BaseDAO<IAttivitaSnapshot, PkId> {

    public List<IAttivitaSnapshot> findAll(Integer firstResult, Integer maxResult);

    /**
     * <pre>
     * Il metodo deve ritornare,se esiste, un oggetto IAttivitaSnapshot precedente alla data passata associato
     * all'attivtà passata. Il metodo deve estratte un solo record di IAttivitaSnapshot associato all'attivita passata
     * ordinando per IAttivitaSnapshot.data desc e ordineAttivita asc . (Il record trovato,se esiste, corrisponde al primo record con
     * IAttivitaSnapshot.data< dataValidita )
     * 
     * &#64;param dataValidita
     * &#64;return Un oggetto IAttivitaSnapshot,se esiste, che corrisponde al primo record con IAttivitaSnapshot.data<
     *         dataValidita
     * </pre>
     */
    public IAttivitaSnapshot findBeforeData(Integer codiceAttivita, Date dataValidita);

    /**
     * <pre>
     * Il metodo deve ritornare,se esiste, un oggetto IAttivitaSnapshot precedente alla data passata e associato
     * all'attivtà passata. Il metodo deve estratte un solo record di IAttivitaSnapshot associato all'attivita passata
     * ordinando per IAttivitaSnapshot.data desc ordineAttivita asc. Inoltre deve verificare che il record ritornato
     * in caso di datavalidita uguale alla datavalidita passata abbia il campo attivitaordine dell'istanza maggiore di quello passato al metodo
     *      
     * &#64;param dataValidita
     * &#64;return Un oggetto IAttivitaSnapshot,se esiste, che corrisponde al primo record con IAttivitaSnapshot.data<
     *         dataValidita
     * </pre>
     */
    public IAttivitaSnapshot findBeforeDataAndOrdine(Integer codiceAttivita, Date dataValidita, Integer ordine);

    /**
     * <pre>
     * Il metodo deve ritornare,se esiste una lista di oggetti IAttivitaSnapshot successivi  alla data passata e associati
     * all'attivtà .La lista deve essere ordinata  per IAttivitaSnapshot.data asc.
     * 
     * &#64;param codiceAttivita
     * &#64;param dataValidita
     * 
     * </pre>
     */
    public List<IAttivitaSnapshot> findAfterData(Integer codiceAttivita, Date dataValidita);

    /**
     * <pre>
     * Il metodo deve ritornare,se esiste una lista di oggetti IAttivitaSnapshot a partire dalla data passata e associati
     * all'attivtà .La lista deve essere ordinata  per IAttivitaSnapshot.data asc.
     * 
     * &#64;param codiceAttivita
     * &#64;param dataValidita
     * 
     * </pre>
     */
    public List<IAttivitaSnapshot> findFromData(Integer codiceAttivita, Date dataValidita);

    /**
     * <pre>
     * Lista di IAttivitaSnapshot (Copia dell'attività a una certa data) ordinata per data snapshot desc
     * &#64;param codiceAttivita
     * &#64;return Ritorna una lista delle copie dell'attività fatte a una certa data (IAttivitaSnapshot)
     * </pre>
     */
    public List<IAttivitaSnapshot> findByAttivita(Integer codiceAttivita);

    public List<Object[]> findListaSchede(Integer codiceAttivita);

    /**
     * Ritorna lo snapshot precedente a quello passato
     * 
     * @param iAttivitaSnapshot
     * @return
     */
    public IAttivitaSnapshot findSnapshotPrecedente(IAttivitaSnapshot iAttivitaSnapshot);

    public List<WsExportBean> findIAttivitaSnapshotByFilter(IAttivitaFilter filter, Date dataEsportazione);

    public List<Integer> findTuttiICodici(Integer codiceAttivitaDaCuiPartire);

    public Map<Date, Integer> findSnapshots(Integer idAttivita, Date dataRicalcolo);
}
