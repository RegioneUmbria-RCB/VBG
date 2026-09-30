package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.TipimovimentoAppIoServizi;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoAppIoServiziId;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model.AppioInterventiPerSofwtareBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model.AppioTipimovimentoEndoBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model.AppioTipimovimentoInterventoBean;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

public interface ITipimovimentoAppIoserviziService extends BaseService<TipimovimentoAppIoServizi, TipimovimentoAppIoServiziId> {

    /**
     * @see ItipimovimentoAppIoDao#isConfiguratoTipomov(Integer)
     * @param codicemovimento
     * @return
     */
    public List<String> isConfiguratoTipomov(Integer codicemovimento);

    public void insert(TipimovimentoAppIoServizi tipimovimentoAppIoServizi);

    public void update(TipimovimentoAppIoServizi tipimovimentoAppIoServizi);

    public List<TipimovimentoAppIoServizi> findAll(Integer firstResult, Integer maxResult);

    public List<TipimovimentoAppIoServizi> findByIdservizio(String idservizio);

    public TipimovimentoAppIoServizi findById(TipimovimentoAppIoServiziId id);

    public void delete(TipimovimentoAppIoServizi tipimovimentoAppIoServizi);

    public List<AppioTipimovimentoEndoBean> findConfigurazioniEndoProcedimenti(String idservizio, String tipomovimento);

    public void insertConfigurazioneEndo(String idservizio, String tipomovimento, Integer codiceInventario, String templateOggetto,
	    String templateMessaggio) throws BusinessValidationException;

    public void updateConfigurazioneEndo(String idservizio, String tipomovimento, Integer codiceInventario, String templateOggetto,
	    String templateMessaggio);

    public void deleteConfigurazioneEndo(String idservizio, String tipomovimento, Integer codiceInventario);

    public void insertConfigurazioneIntervento(String idservizio, String tipomovimento, Integer codiceIntervento, String templateOggetto,
	    String templateMessaggio);

    public void updateConfigurazioneIntervento(String idservizio, String tipomovimento, Integer codiceIntervento, String templateOggetto,
	    String templateMessaggio);

    public void deleteConfigurazioneIntervento(String idservizio, String tipomovimento, Integer codiceIntervento);

    public List<AppioTipimovimentoInterventoBean> findConfigurazioniIntervento(String idservizio, String tipomovimento);

    public List<AppioInterventiPerSofwtareBean> findConfigurazioniInterventoRaggruppati(String idservizio, String tipomovimento);
}
