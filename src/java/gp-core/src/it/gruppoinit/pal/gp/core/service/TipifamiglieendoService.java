package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.helper.FlagPubblicaEnum;

import java.util.List;
import java.util.Set;

public interface TipifamiglieendoService extends BaseService<Tipifamiglieendo, PkId> {

    public List<Tipifamiglieendo> findByFilter(Tipifamiglieendo entity);

    public List<Tipifamiglieendo> findByDescSWeTT(String textToSearch);

    /**
     * Cerca se ci sono record nella tabella impostando di default la ricerca per idcomune e per il software attivo e
     * {@link WebConstants#SOFTWARE_TT}
     * 
     * @see BaseDAO#existsRecords(FilterTable)
     */
    public boolean existsRecords();

    /**
     * <p>
     * Metodo che ritorna una lista di tipi famiglie endo che contengono almeno un categoria endo con degli endo
     * procedimenti attivabili per l'istanza passata. Endo attivabile per l'istanza passata significa:
     * 
     * 1- L'endo non è già stato collegato all'istanza 2- La natura endo configurata è tra quelle passate come
     * ammissibili
     * 
     * @param istanza
     *            : istanza per cui devono essere attivabili gli endo procedimenti
     * 
     * @param listaCodiciNature
     *            : ulteriore filtro che viene applicato sul campo endo procedimenti,cioè dovranno essere considerati
     *            solo gli endo procediementi che hanno come cocice natura tra quelli passati
     * 
     * 
     * 
     *            </p>
     */
    public Set<Tipifamiglieendo> findAllBySoftwareAndTTAndEndoAttivabili(Istanze istanza, List<String> listaCodiciNature);

    @DeletableCacheElements
    public void resetObjectCached();

    public List<Tipifamiglieendo> findTipifamigliaByDescrizione(String testoDaCercare, String tipoRicerca, String campiRicerca,
	    FlagPubblicaEnum pubblicaEnum, Integer firstResult, Integer maxResult);
}
