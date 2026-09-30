package it.gruppoinit.pal.gp.core.features.sorteggi.testata;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.domain.web.SorteggitestataCommand;
import it.gruppoinit.pal.gp.core.features.sorteggi.FiltriSorteggioBean;
import it.gruppoinit.pal.gp.core.features.sorteggi.SorteggioResponse;
import it.gruppoinit.pal.gp.core.features.sorteggi.TipologiaStatoSorteggioIstanzaEnum;
import it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio.SorteggioDettaglioDTO;
import it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio.SorteggioDettaglioPerInserimentoDTO;
import it.gruppoinit.pal.gp.core.service.BaseService;

/**
 * 
 * @author
 */
public interface SorteggitestataService extends BaseService<Sorteggitestata, PkId> {

    /**
     * @see SorteggitestataDAO#findAll(Integer, Integer)
     */
    public List<Sorteggitestata> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see SorteggitestataDAO#findAllSenzaCategoria()
     */
    public List<Sorteggitestata> findAllSenzaCategoria();

    public List<Sorteggitestata> findAllSenzaCategoria(String software);

    /**
     * Esegue il sorteggio
     */
    public SorteggioResponse sorteggia(Sorteggitestata testata, FiltriSorteggioBean filter);

    /**
     * Esegue il sorteggio in base ai criteri della LR 15/2013 Emilia Romagna
     */
    public Sorteggitestata sorteggiaLR152013(SorteggitestataCommand sorteggitestata, FiltriSorteggioBean filter);

    /**
     * Inserisce un movimento e un sorteggidettagliomovimento per ogni istanza sorteggiata
     */
    //public void salvaMovimento(Sorteggitestata entity, Movimenti movimento);
    public void salvaMovimento(Integer idTestataSorteggio, Movimenti movimento, TipologiaStatoSorteggioIstanzaEnum tipologiaStatoSorteggioIstanza);

    @Override
    public void insert(Sorteggitestata entity);

    public void insertConDettagli(Sorteggitestata entity, List<SorteggioDettaglioPerInserimentoDTO> dettaglio);

    public void aggiornaCategoria(Sorteggitestata sorteggitestata);

    public List<SorteggioDettaglioDTO> findDettaglioDTO(Integer idTestata);
}
