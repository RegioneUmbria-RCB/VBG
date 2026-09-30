package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.BlacklistMotivi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface BlacklistMotiviService extends BaseService<BlacklistMotivi, PkId> {

    public StringBuilder updateBlackList();

    /**
     * Trova tutti gli inserimenti in black list data una autorizzazione e l'uso. Se non viene passato l'uso allora non
     * imposta il filtro per mercati_uso
     * 
     * @param idAutorizzazione
     * @param idMercatiUso
     * @param soloGliAttivi
     * @return
     */
    public List<BlacklistMotivi> findByAutorizzazioneEUso(Integer idAutorizzazione, Integer idMercatiUso, boolean soloGliAttivi,
	    BlackListContestoEnum[] contesti);

    List<Date> findAllDataAccertamentoByContesto(BlackListContestoEnum contesto);

    List<BlackListResultBean> getBlackListResultFe(BlackListContestoEnum contesto, String dataaccertamento, Date dalladatablacklist,
	    Date alladatablacklist, Date dalladataiuv, Date alladataiuv, String iuv, String titolare, String cf, Integer firstresult, Integer maxresult);

    List<BlackListResultBean> getBlackListChiuseExport(BlackListContestoEnum contesto);
}
