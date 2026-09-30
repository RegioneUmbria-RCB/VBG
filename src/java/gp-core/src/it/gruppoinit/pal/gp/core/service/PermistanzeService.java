package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.PermistanzeDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Permistanze;
import it.gruppoinit.pal.gp.core.domain.PermistanzeId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface PermistanzeService extends BaseService<Permistanze, PermistanzeId> {

    /**
     * @see PermistanzeDAO#findAll(Integer, Integer)
     */
    public List<Permistanze> findAll(Integer firstResult, Integer maxResult);

    public List<Permistanze> findByFilterTable(FilterTable filterTable);

    /**
     * Trova i permessi per l'istanza ed il responsabile
     * 
     * @param istanza
     * @param responsabile
     * @return
     */
    public boolean checkByIstanzaAndResponsabile(Istanze istanza, Responsabili responsabile);

    /**
     * Trova i permessi per l'istanza
     * 
     * @param istanza
     * @return
     */
    public List<Permistanze> findByIstanza(Istanze istanza);

    // Deve salvare il permesso bypassando le regole di sicurezza
    public void notCheckinsertInFaseDiaccettazioneRuoloIstruttore(Permistanze permistanze);
}
