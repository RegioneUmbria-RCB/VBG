/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeruoli;
import it.gruppoinit.pal.gp.core.domain.IstanzeruoliId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
public interface IstanzeruoliService extends BaseService<Istanzeruoli, IstanzeruoliId> {

    /**
     * Torna la lista dei ruoli per l'istanza ed il responsabile. La funzione verifica quali ruoli presenti nell'istanza
     * corrispondono a quelli dell'operatore. I valori che può tornare sono
     * <ul>
     * <li>CONSENTITO è consentita la modifica/visualizzazione delle informazioni</li>
     * <li>NON_CONSENTITO non è consentita la modifica/visualizzazione delle informazioni</li>
     * <li>SOLA_LETTURA è consentita solamente la visualizzazione delle informazioni</li>
     * <li>SOLA_LETTURA_TUTTI_MOVIMENTI accesso in sola lettura sulle istanze e abilitazione a creare, modificare e
     * cancellare movimenti. (per le amministrazioni interne a cui ha accesso e per le amministrazioni non interne)</li>
     * <li>SOLA_LETTURA_MOVIMENTI_AMM_INTERNA accesso in sola lettura sulle istanze e abilitazione a creare, modificare
     * e cancellare movimenti per le sole amministrazioni interne a cui il ruolo è associato.</li>
     * </ul>
     * 
     * @param istanza
     * @param responsabile
     * @return
     */
    TipoAccessoEnum checkByIstanzaAndResponsabile(Istanze istanza, Responsabili responsabile);

    /**
     * Torna la lista dei ruoli presenti per una istanza
     * 
     * @param istanza
     * @return
     */
    List<Istanzeruoli> findByIstanza(Istanze istanza);

    /**
     * la funzione inserisce il ruolo nelle istanze che hanno come intervento l'alberoproc passato come parametro o una
     * sua foglia
     * 
     * @param codiceAlberoproc
     * @param idRuolo
     */
    public void insertInstanzeRuoloDaAlberoproc(Integer codiceAlberoproc, Integer idRuolo);

    /**
     * la funzione rimuove il ruolo nelle istanze che hanno come intervento l'alberoproc passato come parametro o una
     * sua foglia
     * 
     * @param codiceAlberoproc
     * @param idRuolo
     */
    public void deleteInstanzeRuoloDaAlberoproc(Integer codiceAlberoproc, Integer idRuolo);

    /**
     * la funzione rimuove tutti i ruolo nelle istanze che definiscono come intervento l'alberoproc passato come
     * parametro o una sua foglia
     * 
     * @param idRuolo
     * @param codiceAlberoproc
     */
    public void deleteInstanzeRuoliDaAlberoproc(Integer codiceAlberoproc);
}
