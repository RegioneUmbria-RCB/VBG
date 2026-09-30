package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Istanzedyn2modellitStoricoDAO;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitStorico;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitStoricoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface Istanzedyn2modellitStoricoService extends BaseService<Istanzedyn2modellitStorico, Istanzedyn2modellitStoricoId> {

    /**
     * @see Istanzedyn2modellitStoricoDAO#findAll(Integer, Integer)
     */
    public List<Istanzedyn2modellitStorico> findAll(Integer firstResult, Integer maxResult);

    public List<Istanzedyn2modellitStorico> findByIstanza(Integer codiceistanza);

    public int calcolaProgressivoVersione(Integer codiceistanza);
}
