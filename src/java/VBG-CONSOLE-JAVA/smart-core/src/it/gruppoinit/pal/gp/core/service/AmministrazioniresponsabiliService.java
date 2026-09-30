package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniresponsabili;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface AmministrazioniresponsabiliService extends BaseService<Amministrazioniresponsabili, PkId> {

    /**
     * 
     * @param amminitsrazione
     * @return ritorna una lista di amministrazioniresponsabili filtrati per amministrazione
     */
    public List<Amministrazioniresponsabili> findByAmministrazione(Amministrazioni amministrazione);
}
