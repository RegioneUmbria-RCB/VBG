package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

public interface MercatiUsoService extends BaseService<MercatiUso, PkId> {

    public List<MercatiUso> findByMercato(Mercati mercati);

    /**
     * Il metodo filtra i giorni (mercatoUso) per descrizione (ilike) e mercato. Entrambi i campi sono obbligatori, non
     * viene passato il mercato la ricerca deve restituire una lista vuota
     * 
     * @param textToSearch
     * @param mercati
     * @return
     */
    public List<MercatiUso> findDescrizioneAndMercato(String textToSearch, Mercati mercati);

    public List<MercatiUso> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult);
}
