package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;
import it.gruppoinit.pal.gp.core.domain.helper.RaggruppamentocausalioneriHelper;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface RaggruppamentocausalioneriDAO extends BaseDAO<Raggruppamentocausalioneri, PkId> {

    /**
     * Recupera la lista di raggruppamenti causali oneri per il software corrente ordinati per descrizione
     * 
     */
    public List<Raggruppamentocausalioneri> findAll(Integer firstResult, Integer maxResult);

    /**
     * Recupera tutti i raggruppamenti degli oneri associati all'istanza
     * 
     * @param istanza
     * @return
     */
    public List<Raggruppamentocausalioneri> findByIstanza(Istanze istanza);

    /**
     * Recupera tutti i raggruppamenti degli oneri associati all'istanza e raggruppati per data pagamento
     * 
     * @param istanza
     * @return
     */
    public List<RaggruppamentocausalioneriHelper> findByIstanzaAndDataPagamento(Istanze istanza);
}
