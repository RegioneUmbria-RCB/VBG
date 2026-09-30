package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CommedilizieTipologieDAO;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface CommedilizieTipologieService extends BaseService<CommedilizieTipologie, PkId> {

    /**
     * @see CommedilizieTipologieDAO#findAll(Integer, Integer)
     */
    public List<CommedilizieTipologie> findAll(Integer firstResult, Integer maxResult);

    public List<CommedilizieTipologie> findByFilterTable(FilterTable filterTable);

    /**
     * Torna la lista delle CommedilizieTipologie di un'Amministrazione
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<CommedilizieTipologie> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);

    public boolean aggiungiRuolo(Integer codiceTipologia, Integer idRuolo);

    public boolean eliminaRuolo(Integer codiceTipologia, Integer idRuolo);

    public List<CommedilizieTipologie> findByRuolo(Integer idRuolo, Integer firstResult, Integer maxResult);
}
