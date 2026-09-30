package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniruoli;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniruoliId;

import java.util.List;

public interface AmministrazioniruoliService extends BaseService<Amministrazioniruoli, AmministrazioniruoliId> {

    /**
     * 
     * @param amminitsrazione
     * @return ritorna una lista di amministrazioniresponsabili filtrati per amministrazione
     */
    public List<Amministrazioniruoli> findByAmministrazione(Amministrazioni amministrazione);
}
