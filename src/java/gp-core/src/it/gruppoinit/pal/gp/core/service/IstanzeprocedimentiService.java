/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.IstanzeprocedimentiId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocedimentiHelper;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
public interface IstanzeprocedimentiService extends BaseService<Istanzeprocedimenti, IstanzeprocedimentiId> {

    public List<Istanzeprocedimenti> findByIstanze(Istanze istanze);

    public List<Istanzeprocedimenti> findByIstanze(Integer codiceIstanze);

    /**
     * Inserisce la lista di tutte le istanze procedimenti passati
     * 
     * @param listIstanzeprocedimentiTot
     */
    public void insertListIstanzeprocedimenti(List<Istanzeprocedimenti> listIstanzeprocedimentiTot);

    /**
     * La funzione controlla se per l'endo attivato dell'istanza è stato inserito il corrispettivo movimento. <br />
     * Il movimento non viene inserito se il record di istanzeprocedimenti ha il <b>flag acquisito = true</b>. <br />
     * Se istanzeprocedimenti.acquisito = true ed il movimento è stato inserito come scadenza (ossia non è stato
     * effettuato) allora viene cancellato dalle scadenze dell'elaborazione. <br />
     * Il movimento che si andrà ad inserire sarà quello specificato dalla colonna TIPOMOVIMENTO dell'endo specificato
     * se l'endo è di un software diverso da <b>'TT'</b>.<br/>
     * Se l'endo appartiene al software <b>'TT'</b> allora il movimento cerca tra i movimenti configurati in
     * inventarioprocedimentisoftwares e se non lo trova sempre nella colonna TIPOMOVIMENTO dell'endo.
     * 
     * @param entity
     */
    public void inserisciMovimentoIstanzeprocedimenti(Istanzeprocedimenti entity);

    public List<Istanzeprocedimenti> findByFilterTable(FilterTable filterTable);

    /**
     * Torna una lista di IstanzeprocedimentiHelper con indicati per ognun elemento i movimenti di invio/ritorno
     * 
     * @param istanze
     * @return
     */
    public List<IstanzeprocedimentiHelper> findRiepilogoEndo(Istanze istanze);

    /**
     * <pre>
     * Ritorna il numero di record presenti nella tabella filtrata per l'istanza (parametro codiceIstanza)
     * 
     * &#64;param codiceIstanza
     * &#64;return
     * </pre>
     */
    public int countByIstanza(Integer codiceIstanza);

    /**
     * <pre>
     * Ritorna il numero di record presenti nella tabella filtrata per inventarioprocedimento  (parametro codiceProcedimento)
     * 
     * &#64;param codiceIstanza
     * &#64;return
     * </pre>
     */
    public int countByInventarioprocedimento(Integer codiceProcedimento);
}
