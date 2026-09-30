package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoRichiesteDAO;
import it.gruppoinit.pal.gp.core.domain.FoRichieste;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author
 */
public interface FoRichiesteService extends BaseService<FoRichieste, PkId> {

    public static final int RICHIESTA_MODIFICA_DATI = 1;
    public static final int RICHIESTA_NUOVA_REGISTRAZIONE = 2;

    /**
     * @see FoRichiesteDAO#findAll(Integer, Integer)
     */
    public List<FoRichieste> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see FoRichiesteDAO#findByFilterTable(FilterTable)
     */
    public List<FoRichieste> findByFilterTable(FilterTable filterTable);

    /**
     * Torna la lista delle richieste provenienti da frontoffice che non sono state lette ordinate per datarichiesta ASC
     * 
     * @return
     */
    public List<FoRichieste> findRichiesteNonLette(Integer firstResult, Integer maxResult);

    /**
     * Torna il numero delle richieste provenienti da frontoffice che non sono state lette
     * 
     * @return
     */
    public int countRichiesteNonLette();
}
