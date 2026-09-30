package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoAppIoServizi;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoAppIoServiziId;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model.AppioTipimovimentoEndoBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model.AppioTipimovimentoInterventoBean;

public interface ItipimovimentoAppIoDao extends BaseDAO<TipimovimentoAppIoServizi, TipimovimentoAppIoServiziId> {

    /**
     * Un movimento è configurato se presente nella tabella tipimovimento_app_io_servizi. Nel caso che vengano
     * registrate righe su tipimov_appioservizi_int allora queste funzionano come filtri ulteriori, ovvero se l'istanza
     * ha un intervento collegato a questa configurazione (in maniera ereditaria) allora significa che il messaggio deve
     * essere mandato per quel servizio altrimenti no.
     * 
     * @param codiceMovimento
     * @return La lista degli identificativi servizi per i quali inviare i messaggi
     */
    List<String> isConfiguratoTipomov(Integer codiceMovimento);

    List<TipimovimentoAppIoServizi> findByIdservizio(String idservizio);

    void deleteTipimovAppioServiziEndo(String idservizio, String tipomov);

    void deleteTipimovAppioServiziEndo(String idservizio, String tipomov, Integer codiceinventario);

    void deleteTipimovAppioServiziInt(String idservizio, String tipomov);

    void deleteTipimovAppioServiziInt(String idservizio, String tipomov, Integer codiceIntervento);

    List<AppioTipimovimentoEndoBean> findConfigurazioniEndoProcedimenti(String idservizio, String tipomovimento);

    void insertConfigurazioneEndo(String idservizio, String tipomovimento, Integer codiceInventario, String templateOggetto,
	    String templateMessaggio);

    void updateConfigurazioneEndo(String idservizio, String tipomovimento, Integer codiceInventario, String templateOggetto,
	    String templateMessaggio);

    List<AppioTipimovimentoInterventoBean> findConfigurazioniIntervento(String idservizio, String tipomovimento);

    void insertConfigurazioneIntervento(String idservizio, String tipomovimento, Integer codiceIntervento, String templateOggetto,
	    String templateMessaggio);

    void updateConfigurazioneIntervento(String idservizio, String tipomovimento, Integer codiceIntervento, String templateOggetto,
	    String templateMessaggio);
}
