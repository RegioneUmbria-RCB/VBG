package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.TipimovimentoAppIoServizi;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoAppIoServiziId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model.AppioInterventiPerSofwtareBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model.AppioTipimovimentoEndoBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model.AppioTipimovimentoInterventoBean;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class TipimovimentoAppIoserviziServiceImpl extends BaseServiceImpl<TipimovimentoAppIoServizi, TipimovimentoAppIoServiziId>
	implements ITipimovimentoAppIoserviziService {

    private static final String SOFTWAREKEY_SEPARATOR = "---";
    @Autowired
    private ItipimovimentoAppIoDao tipimovimentoAppIoDao;

    @Override
    public List<String> isConfiguratoTipomov(Integer codiceMovimento) {

	return this.tipimovimentoAppIoDao.isConfiguratoTipomov(codiceMovimento);
    }

    @Override
    public List<TipimovimentoAppIoServizi> findAll(Integer firstResult, Integer maxResult) {

	return tipimovimentoAppIoDao.findAll(firstResult, maxResult);
    }

    @Override
    public TipimovimentoAppIoServizi findById(TipimovimentoAppIoServiziId id) {

	return tipimovimentoAppIoDao.findById(id);
    }

    @Override
    public void insert(TipimovimentoAppIoServizi tipimovimentoAppIoServizi) {

	if (validateEntity(tipimovimentoAppIoServizi)) {
	    tipimovimentoAppIoDao.insert(tipimovimentoAppIoServizi);
	}
    }

    @Override
    public void delete(TipimovimentoAppIoServizi tipimovimentoAppIoServizi) {

	tipimovimentoAppIoDao.deleteTipimovAppioServiziEndo(tipimovimentoAppIoServizi.getId().getIdentificativoServizio(),
		tipimovimentoAppIoServizi.getId().getTipomovimento());
	tipimovimentoAppIoDao.deleteTipimovAppioServiziInt(tipimovimentoAppIoServizi.getId().getIdentificativoServizio(),
		tipimovimentoAppIoServizi.getId().getTipomovimento());
	tipimovimentoAppIoDao.delete(tipimovimentoAppIoServizi);
    }

    @Override
    public List<TipimovimentoAppIoServizi> findByIdservizio(String idservizio) {

	return this.tipimovimentoAppIoDao.findByIdservizio(idservizio);
    }

    @Override
    public void update(TipimovimentoAppIoServizi tipimovimentoAppIoServizi) {

	if (validateEntity(tipimovimentoAppIoServizi)) {
	    tipimovimentoAppIoDao.update(tipimovimentoAppIoServizi);
	}
    }

    @Override
    protected Class<TipimovimentoAppIoServizi> getEntityClass() {

	return TipimovimentoAppIoServizi.class;
    }

    @Override
    public List<AppioTipimovimentoEndoBean> findConfigurazioniEndoProcedimenti(String idservizio, String tipomovimento) {

	return tipimovimentoAppIoDao.findConfigurazioniEndoProcedimenti(idservizio, tipomovimento);
    }

    @Override
    public void insertConfigurazioneEndo(String idservizio, String tipomovimento, Integer codiceInventario, String templateOggetto,
	    String templateMessaggio) throws BusinessValidationException {

	List<AppioTipimovimentoEndoBean> confEsistenti = tipimovimentoAppIoDao.findConfigurazioniEndoProcedimenti(idservizio, tipomovimento);
	for (AppioTipimovimentoEndoBean e : confEsistenti) {
	    if (e.getCodiceinventario().equals(codiceInventario)) {
		throw new BusinessValidationException("Endoprocedimento " + e.getProcedimento() + "(" + e.getCodiceinventario() + ") già registrato");
	    }
	}
	tipimovimentoAppIoDao.insertConfigurazioneEndo(idservizio, tipomovimento, codiceInventario, templateOggetto, templateMessaggio);
    }

    @Override
    public void updateConfigurazioneEndo(String idservizio, String tipomovimento, Integer codiceInventario, String templateOggetto,
	    String templateMessaggio) {

	tipimovimentoAppIoDao.updateConfigurazioneEndo(idservizio, tipomovimento, codiceInventario, templateOggetto, templateMessaggio);
    }

    @Override
    public void deleteConfigurazioneEndo(String idservizio, String tipomovimento, Integer codiceInventario) {

	tipimovimentoAppIoDao.deleteTipimovAppioServiziEndo(idservizio, tipomovimento, codiceInventario);
    }

    @Override
    public void insertConfigurazioneIntervento(String idservizio, String tipomovimento, Integer codiceIntervento, String templateOggetto,
	    String templateMessaggio) {

	List<AppioTipimovimentoInterventoBean> confEsistenti = tipimovimentoAppIoDao.findConfigurazioniIntervento(idservizio, tipomovimento);
	for (AppioTipimovimentoInterventoBean e : confEsistenti) {
	    if (e.getCodiceintervento().equals(codiceIntervento)) {
		throw new BusinessValidationException("Intervento " + e.getIntervento() + "(" + e.getCodiceintervento() + ") già registrato");
	    }
	}
	tipimovimentoAppIoDao.insertConfigurazioneIntervento(idservizio, tipomovimento, codiceIntervento, templateOggetto, templateMessaggio);
    }

    @Override
    public void updateConfigurazioneIntervento(String idservizio, String tipomovimento, Integer codiceIntervento, String templateOggetto,
	    String templateMessaggio) {

	tipimovimentoAppIoDao.updateConfigurazioneIntervento(idservizio, tipomovimento, codiceIntervento, templateOggetto, templateMessaggio);
    }

    @Override
    public void deleteConfigurazioneIntervento(String idservizio, String tipomovimento, Integer codiceIntervento) {

	tipimovimentoAppIoDao.deleteTipimovAppioServiziInt(idservizio, tipomovimento, codiceIntervento);
    }

    @Override
    public List<AppioTipimovimentoInterventoBean> findConfigurazioniIntervento(String idservizio, String tipomovimento) {

	return tipimovimentoAppIoDao.findConfigurazioniIntervento(idservizio, tipomovimento);
    }

    @Override
    public List<AppioInterventiPerSofwtareBean> findConfigurazioniInterventoRaggruppati(String idservizio, String tipomovimento) {

	List<AppioInterventiPerSofwtareBean> ret = new ArrayList<AppioInterventiPerSofwtareBean>();
	Map<String, List<AppioTipimovimentoInterventoBean>> mappaPerSoftware = new HashMap<String, List<AppioTipimovimentoInterventoBean>>();
	List<AppioTipimovimentoInterventoBean> list = tipimovimentoAppIoDao.findConfigurazioniIntervento(idservizio, tipomovimento);
	for (AppioTipimovimentoInterventoBean appioBean : list) {
	    String softwareKey = getSoftwareKey(appioBean);
	    List<AppioTipimovimentoInterventoBean> dati = mappaPerSoftware.get(softwareKey);
	    if (dati == null) {
		dati = new ArrayList<AppioTipimovimentoInterventoBean>();
	    }
	    dati.add(appioBean);
	    mappaPerSoftware.put(softwareKey, dati);
	}
	for (Entry<String, List<AppioTipimovimentoInterventoBean>> entry : mappaPerSoftware.entrySet()) {
	    String softwareKey = entry.getKey();
	    CodiceDescrizioneBean b = getSoftwareBeanDaKey(softwareKey);
	    ret.add(new AppioInterventiPerSofwtareBean(b.getCodice(), b.getDescrizione(), entry.getValue()));
	}
	return ret;
    }

    private CodiceDescrizioneBean getSoftwareBeanDaKey(String softwareKey) {

	String[] dati = softwareKey.split(SOFTWAREKEY_SEPARATOR);
	return new CodiceDescrizioneBean(dati[0], dati[1]);
    }

    private String getSoftwareKey(AppioTipimovimentoInterventoBean appioBean) {

	return appioBean.getCodicesoftware() + SOFTWAREKEY_SEPARATOR + appioBean.getDescrizionesoftware();
    }
}
