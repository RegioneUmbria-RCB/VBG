package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

import java.util.List;

public interface ConfigurazioneutenteDAO extends BaseDAO<Configurazioneutente, ConfigurazioneutenteId> {

    /**
     * Torna le configurazioni di un operatore
     * 
     * @param responsabile
     * @return
     */
    public List<Configurazioneutente> findByResponsabile(Responsabili responsabile);
}
