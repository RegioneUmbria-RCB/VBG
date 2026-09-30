package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.BandiAlberoprocDAO;
import it.gruppoinit.pal.gp.core.domain.BandiAlberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface BandiAlberoprocService extends BaseService<BandiAlberoproc, PkId> {

    /**
     * @see BandiAlberoprocDAO#findAll(Integer, Integer)
     */
    public List<BandiAlberoproc> findAll(Integer firstResult, Integer maxResult);

    public List<BandiAlberoproc> findByBandi(Integer codiceBando);

    public int findMaxOrdine(Integer codiceBando);

    /**
     * Scambia il valore del campo ordine quello inferiore a quello superiore
     * 
     * @param codiceIstanza
     * @param codiceIstanzaSup
     */
    public void updateUpOrdine(Integer codiceBandoAlberoProc, Integer codiceBandoAlberoProcSup);

    /**
     * Scambia il valore del campo ordine quello superiore a quello inferiore
     * 
     * @param codiceIstanza
     * @param codiceIstanzaSup
     */
    public void updateDownOrdine(Integer codiceBandoAlberoProc, Integer codiceBandoAlberoProcInf);

    /**
     * @see BandiAlberoprocDAO#findDistinctMercatiByBando(Integer codiceBando)
     */
    public List<Integer> findDistinctMercatiByBando(Integer codiceBando);

    /**
     * @see BandiAlberoprocDAO#findDistinctMercatiUsoByBando(Integer codiceBando)
     */
    public List<Integer> findDistinctMercatiUsoByBando(Integer codiceBando);
}
