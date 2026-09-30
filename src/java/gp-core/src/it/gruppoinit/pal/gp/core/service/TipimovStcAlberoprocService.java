package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipimovStcAlberoprocDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAlberoproc;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface TipimovStcAlberoprocService extends BaseService<TipimovStcAlberoproc, PkId> {

    /**
     * @see TipimovStcAlberoprocDAO#findAll(Integer, Integer)
     */
    public List<TipimovStcAlberoproc> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see TipimovStcAlberoprocDAO#findByTipimovimento(TipimovimentoId)
     */
    public List<TipimovStcAlberoproc> findByTipimovimento(TipimovimentoId tipimovimentoId);

    /**
     * Torna la lista delle TipimovStcAlberoproc di un'Amministrazione
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<TipimovStcAlberoproc> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);

    public List<TipimovStcAlberoproc> findByTipimovimentoAndAmministrazione(String tipomovimento, Integer codiceAmministrazione);
}
