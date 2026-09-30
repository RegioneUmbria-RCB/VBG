package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FormegiuridicheDAO;
import it.gruppoinit.pal.gp.core.domain.Formegiuridiche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author gianpaolot
 * 
 */
public interface FormegiuridicheService extends BaseService<Formegiuridiche, PkId> {

    /**
     * @see FormegiuridicheDAO#findAll(Integer, Integer)
     */
    public List<Formegiuridiche> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see FormegiuridicheDAO#findByFilterTable(FilterTable filterTable)
     */
    public List<Formegiuridiche> findByFilterTable(FilterTable filterTable);

    /**
     * esegue una query su FORMEGIURIDICHE.CODICECCIIA E TORNA IL PRIMO RECORD o null se non trovato niente
     * 
     * @param riFormegiuridicheCodice
     * @return
     */
    public Formegiuridiche findByCodiceRiFormegiuridiche(String riFormegiuridicheCodice);
}
