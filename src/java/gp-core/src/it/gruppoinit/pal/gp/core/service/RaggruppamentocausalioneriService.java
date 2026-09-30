package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.RaggruppamentocausalioneriDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;
import it.gruppoinit.pal.gp.core.domain.helper.RaggruppamentocausalioneriHelper;

import java.util.List;

/**
 * 
 * @author
 */
public interface RaggruppamentocausalioneriService extends BaseService<Raggruppamentocausalioneri, PkId> {

    /**
     * @see RaggruppamentocausalioneriDAO#findAll(Integer, Integer)
     */
    public List<Raggruppamentocausalioneri> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see RaggruppamentocausalioneriDAO#findByIstanza(Istanze istanza)
     */
    public List<Raggruppamentocausalioneri> findByIstanza(Istanze istanza);

    /**
     * @see RaggruppamentocausalioneriDAO#findByIstanzaAndDataPagamento(Istanze istanza)
     */
    public List<RaggruppamentocausalioneriHelper> findByIstanzaAndDataPagamento(Istanze istanza);
}
