/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoli;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoliId;

import java.util.List;
import java.util.Set;

/**
 * @author francescop
 * 
 */
public interface AlberoprocRuoliService extends BaseService<AlberoprocRuoli, AlberoprocRuoliId> {

    public List<AlberoprocRuoli> findByAlberoprocId(Integer codice);

    public void deleteByAlberoprocId(Integer codice);

    /**
     * Il metodo trova le voci dell'alberoproc per i ruoli del responsabile. Se il responsabile non ha ruoli torna Set
     * vuoto
     * 
     * @param idRuoli
     * @return
     */
    Set<Integer> trovaVociPerRuoliDelResponsabile(Integer codiceResponsabile);

    /**
     * il metodo verifica se la voce dell'albero è assegnabile per i ruoli dell'operatore
     * 
     * @param codiceAlberoproc
     * @param codiceResponsabile
     * @return
     */
    public boolean isAssegnabilePerRuoloDelResponsabile(Integer codiceAlberoproc, Integer codiceResponsabile);
}
