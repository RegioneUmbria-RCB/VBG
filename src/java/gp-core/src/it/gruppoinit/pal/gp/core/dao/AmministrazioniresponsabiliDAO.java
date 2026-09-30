package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniresponsabili;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface AmministrazioniresponsabiliDAO extends BaseDAO<Amministrazioniresponsabili, PkId> {

    /**
     * 
     * @param amminitsrazione
     * @return ritorna una lista di amministrazioniresponsabili filtrati per amministrazione
     */
    public List<Amministrazioniresponsabili> findByAmministrazione(Amministrazioni amministrazione);
}
