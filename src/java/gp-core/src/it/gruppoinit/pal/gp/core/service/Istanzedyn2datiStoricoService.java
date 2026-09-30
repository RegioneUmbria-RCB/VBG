package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Istanzedyn2datiStoricoDAO;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiStoricoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface Istanzedyn2datiStoricoService extends BaseService<Istanzedyn2datiStorico, Istanzedyn2datiStoricoId> {

    /**
     * @see Istanzedyn2datiStoricoDAO#findAll(Integer, Integer)
     */
    public List<Istanzedyn2datiStorico> findAll(Integer firstResult, Integer maxResult);

    public List<Istanzedyn2datiStorico> findByIstanza(Integer codiceistanza);

    @Override
    public void insert(Istanzedyn2datiStorico entity);
}
