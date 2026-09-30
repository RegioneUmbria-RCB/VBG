/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Amministrazioniruoli;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabiliruoli;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;
import java.util.Set;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
public interface RuoliService extends BaseService<Ruoli, PkId> {

    public List<Ruoli> findByFilterTable(FilterTable filterTable);

    /**
     * Filtra le la lista dei ruoli in base alla descrizione inserita
     * 
     * @param ruoli
     * @return
     */
    public List<Ruoli> findByFilter(Ruoli ruoli);

    /**
     * Metodo per salvare i ruoli nella tabella Responsabiliruoli
     * 
     * @param ruolo
     * @param responsabiliruolis
     */
    public void saveRuoliResponsabili(Ruoli ruolo, Set<Responsabiliruoli> responsabiliruolis);

    /**
     * Metodo per salvare i ruoli nella tabella Amministrazioniruoli
     * 
     * @param ruolo
     * @param responsabiliruolis
     */
    public void saveRuoliAmministrazioni(Ruoli ruolo, Set<Amministrazioniruoli> amministrazioniruolis);
}
