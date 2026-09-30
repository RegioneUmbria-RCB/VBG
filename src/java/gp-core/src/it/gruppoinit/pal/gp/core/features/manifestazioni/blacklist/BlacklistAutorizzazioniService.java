package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.BlacklistAutorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BlacklistMotivi;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.BlackListWrapper;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface BlacklistAutorizzazioniService extends BaseService<BlacklistAutorizzazioni, PkId> {

    List<BlackListAttivaBean> findAutorizzazioniInBlackListAttive();

    /**
     * Trova gli identificativi delle autorizzazioni in blackList e l'eventuale id di mercatiuso con il quale è entrato
     * in black list Serve a determinare se è entrato in blacklist come spuntista o come concessionario di una specifica
     * giornata
     * 
     * @return
     */
    public BlackListWrapper findAutorizzazioniInBlackListAttivePerGiornata(Integer idGiornata, BlackListContestoEnum[] contesti);

    int AutorizzazioniInBlackListAttive();

    List<BlacklistAutorizzazioni> findByBlackListMotivo(Integer codice);

    /**
     * Il metodo aggiunge una autorizzazione alla blacklist. Viene passato il motivo della black list e la giornata di
     * riferimento. Se entra in blacklist uno spuntista allora vengono messe in black list anche tutte le autorizzazioni
     * collegate. Se entra in blacklist un concessionario, viene messa in black list la sua concessione ma solamente per
     * quel mercato_uso Se il concessionario entra in black list come spuntista ( perchè è andato a fare lo spuntista da
     * un'altra parte ) viene considerato a tutti gli effetti come uno spuntista per cui anche le sue
     * autorizzazioni/concessioni collegate entreranno in blacklist
     * 
     * 
     * @param motivo
     * @param giornata
     * @param titolare
     */
    void aggiungiABlackList(BlacklistMotivi motivo, MercatipresenzeD giornata, Anagrafe titolare);
}
