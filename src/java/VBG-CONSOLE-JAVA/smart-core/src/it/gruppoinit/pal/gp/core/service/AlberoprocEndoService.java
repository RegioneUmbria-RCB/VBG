package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoId;
import it.gruppoinit.pal.gp.core.domain.helper.NatureEndoIncompatibili;

import java.util.List;

public interface AlberoprocEndoService extends BaseService<AlberoprocEndo, AlberoprocEndoId> {

    /**
     * Metodo che restituisce la lista degli AlberoprocEndo filtrati per il codice dell'Alberoproc ordinati per
     * flagPrincipale, inventarioprocedimenti.ordine
     * 
     * @param codice
     * @return
     */
    public List<AlberoprocEndo> findAllByAlberoproc(String idcomunebase, Integer codice);

    /**
     * Metodo che restituisce la lista degli AlberoprocEndo filtrati per il codice dell'Alberoproc ordinati per
     * flagPrincipale, inventarioprocedimenti.ordine
     * 
     * @param codice
     * @param excludeDisabled
     * @return
     */
    public List<AlberoprocEndo> findAllByAlberoproc(String idcomune, Integer codice, boolean excludeDisabled);

    /**
     * Metodo che restituisce la lista degli AlberoprocEndo filtrati per il codice dell'Alberoproc , flag pubblica =
     * true, flag inventarioprocedimenti.disabilitato != true e ordinati per inventarioprocedimenti.ordine ASC
     * 
     * @param codice
     * @return
     */
    public List<AlberoprocEndo> findAllByAlberoprocAndFlagPubblica(String idcomune, Integer codice);

    /**
     * Trova la lista degli endo procedimenti per la voce di alberoproc. Il metodo risale ricorsivamente la gerarchia
     * delle voci di albero. La lista torna ordinata per principale desc, procedimento asc
     * 
     * @param alberoproc
     * @return
     */
    public List<AlberoprocEndo> findEndoprocedimentiHierarchy(Alberoproc alberoproc);

    /**
     * <pre>
     * Il metodo torna una lista di oggetti albero procedimenti endo filtrati per una lista di endoprocedimenti 
     * [ query restriction: <b>in (listcodici)</b>] e per software [query restriction: <b>eq (codiceSoftware)</b>].
     * Se escludiAlberoprocDisabilitati=true dal risultato vengono esclusi i record per i quali la voce dell'albero 
     * risulta disabilitata (ALBEROPROC_SC_ATTIVA=1 è disabilitata).
     * 
     * @param listCodiciProcedimenti
     * @param codiceSoftware
     * @param escludiAlberoprocDisabilitati
     * 
     * @return lista di record albero procedimenti endo
     * </pre>
     */
    public List<AlberoprocEndo> findByEndoprocedimentiAndSoftware(List<Integer> listCodiciProcedimenti, String codiceSoftware,
	    boolean escludiAlberoprocDisabilitati);

    /**
     * Il metodo effettua la verifica della compatibiltà fra la Naturaendo principale del procedimento passato come
     * primo argomento e le nature degli endo i cui codici (inventarioprocedimenti.id.codice) sono contenuti nella lista
     * passata come secondo argomento. La natura principale del procedimento viene determinata secondio la seguente
     * logica: Se uno dei codici endo passati nella lista è l'endo principale per il procedimento (flagPrincipale ==
     * true in AlberoprocEndo per id.codiceinventario = 'idAlberoProc') allora la natura dell'endo principale viene
     * considerata la natura principale del procedimento. Se nessuno degli endo passati nella lista è l'endo principale
     * allora la natura principale del procedimento viene recuperata da alberoproc.tipoProcedura.naturaEndo dove
     * alberoproc.id.codice = 'idAlberoProc'. tutte le incompatibilità rilevate vengono restituite in una lista di
     * oggetti {@link NatureEndoIncompatibili}.
     * 
     * @param idAlberoProc
     *            : id del procedimento principale
     * @param codiciEndo
     *            : {@link List} di codici di endoprocedimenti della cui natura deve essere verificata la compatibilità
     *            con la natura principale.
     * @return
     */
    public List<NatureEndoIncompatibili> checkNatureEndoIncompatibili(String idcomune, Integer idAlberoProc, List<String> codiciEndo);

    boolean isPrincipale(String idComune, Integer idAlberoProc, Integer codiceinventario);
}
