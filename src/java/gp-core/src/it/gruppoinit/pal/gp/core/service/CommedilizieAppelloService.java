package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.CommedilizieAppelloDAO;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppelloPratiche;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

/**
 * 
 * @author gianpaolot
 */
public interface CommedilizieAppelloService extends BaseService<CommedilizieAppello, PkId> {

    /**
     * @see CommedilizieAppelloDAO#findAll(Integer, Integer)
     */
    public List<CommedilizieAppello> findAll(Integer firstResult, Integer maxResult);

    public List<CommedilizieAppello> findByFilterTable(FilterTable filterTable);

    /**
     * Lista dei responsasili associati a una commissione
     * 
     * @param commissioniedilizieT
     * @return
     */
    public List<CommedilizieAppello> findByCommissioniT(CommissioniedilizieT commissioniedilizieT);

    /**
     * Torna la lista delle CommedilizieAppello di un'Amministrazione
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<CommedilizieAppello> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);

    public void insert(CommedilizieAppello entity, List<Integer> codiciCommissioniEdilizieR);

    /**
     * Recupera le righe di appello pratiche
     * 
     * @param idAppello
     * @return
     */
    public List<CommedilizieAppelloPratiche> findCommEdilizieAppelloPraticheByAppello(Integer idAppello);

    public void update(CommedilizieAppello commedilizieappello, List<Integer> codiciCommissioniEdilizieR);

    /**
     * Recupera le righe di appello per le quali esiste una riga di CommissioniEdilizieR
     * 
     * @param idCommissioniedilizieR
     * @return
     */
    public List<CommedilizieAppello> findByCommissioniEdilizieR(Integer idCommissioniedilizieR);
}
