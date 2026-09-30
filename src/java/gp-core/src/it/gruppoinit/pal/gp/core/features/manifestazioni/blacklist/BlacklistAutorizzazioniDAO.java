package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BlacklistAutorizzazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface BlacklistAutorizzazioniDAO extends BaseDAO<BlacklistAutorizzazioni, PkId> {

    /**
     * Trova gli identificativi delle autorizzazioni in blackList e l'eventuale id di mercatiuso con il quale è entrato
     * in black list Serve a determinare se è entrato in blacklist come spuntista o come concessionario
     * 
     * @return
     */
    List<BlackListAttivaBean> findAutorizzazioniInBlackListAttive();

    /**
     * Trova gli identificativi delle autorizzazioni in blackList e l'eventuale id di mercatiuso con il quale è entrato
     * in black list. Serve a determinare se è entrato in blacklist come spuntista o come concessionario per uno
     * specifico id di mercati_uso
     * 
     * @return
     */
    List<BlackListAttivaBean> findAutorizzazioniInBlackListAttivePerMercatiUso(Integer idMercatiUso, BlackListContestoEnum[] contesti);
}
