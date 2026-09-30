package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAlberoproc;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface TipimovStcAlberoprocDAO extends BaseDAO<TipimovStcAlberoproc, PkId> {

    /**
     * Non Implementata perché deve sempre essere richiamata la ricerca per tipomovimento
     * 
     */
    public List<TipimovStcAlberoproc> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle configurazioni per un determinato movimento
     * 
     * @param tipimovimentoId
     * @return
     */
    public List<TipimovStcAlberoproc> findByTipimovimento(TipimovimentoId tipimovimentoId);
}
