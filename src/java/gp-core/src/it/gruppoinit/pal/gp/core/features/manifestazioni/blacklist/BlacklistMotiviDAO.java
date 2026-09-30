package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BlacklistMotivi;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface BlacklistMotiviDAO extends BaseDAO<BlacklistMotivi, PkId> {

    /**
     * Torna una lista formata da DETT_POSIZIONE_DEBITORIA.ID e MERCATIPRESENZE_D.ID per le posizioni debitorie che: -
     * non sono già in una blacklist attiva - non sono scollegate da giornate di mercato
     * 
     * @return
     */
    List<PosizioneDaAggiungereABlackList> findElencoPosizioniDaAggiungereABlackList(List<String> statiPagabili, BlackListContestoEnum contesto);
    
    /**
     * Torna l'elenco delle blacklist aperte
     * 
     * @return
     */
    List<ElementoBlackListDaChiudereBean> findBlackListAperte(BlackListContestoEnum contesto);

    List<Date> findAllDataAccertamentoByContesto(BlackListContestoEnum contesto);

    List<BlackListResultBean> getBlackListResultFe(BlackListContestoEnum contesto, String dataaccertamento, Date dalladatablacklist,
	    Date alladatablacklist, Date dalladataiuv, Date alladataiuv, String iuv, String titolare, String cf, Integer firstresult, Integer maxresult);

    List<BlackListResultBean> getBlackListChiuseExport(BlackListContestoEnum contesto);
}
