package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

public interface TipifamiglieendoService extends BaseService<Tipifamiglieendo, PkId> {

    public List<Tipifamiglieendo> findByFilter(Tipifamiglieendo entity, String idcomune);

    public List<Tipifamiglieendo> findByDescSWeTT(String textToSearch);

    public List<Tipifamiglieendo> findByDescAndSW(String textToSearch, String[] software, String idcomune);

    /**
     * Cerca se ci sono record nella tabella impostando di default la ricerca per idcomune e per il software attivo e
     * {@link WebConstants#SOFTWARE_TT}
     * 
     * @see BaseDAO#existsRecords(FilterTable)
     */
    public boolean existsRecords();

    @DeletableCacheElements
    public void resetObjectCached();

    public List<Tipifamiglieendo> findTipifamigliaByDescrizione(String testoDaCercare, String tipoRicerca, String campiRicerca, Integer firstResult,
	    Integer maxResult);

    public List<Tipifamiglieendo> findAllBase(Integer firstResult, Integer maxResults);
}
