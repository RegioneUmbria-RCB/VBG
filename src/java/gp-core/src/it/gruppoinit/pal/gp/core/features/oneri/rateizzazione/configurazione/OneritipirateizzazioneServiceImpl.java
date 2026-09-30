package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.TipiScadenza;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class OneritipirateizzazioneServiceImpl extends BaseServiceImpl<Oneritipirateizzazione, PkId> implements OneritipirateizzazioneService {

    private OneritipirateizzazioneDAO oneritipirateizzazioneDAO;
    private TipiScadenzaDAO tipiScadenzaDAO;

    @Autowired
    public void setOneritipirateizzazioneDAO(OneritipirateizzazioneDAO oneritipirateizzazioneDAO) {

	this.oneritipirateizzazioneDAO = oneritipirateizzazioneDAO;
    }

    @Autowired
    public void setTipiScadenzaDAO(TipiScadenzaDAO tipiScadenzaDAO) {

	this.tipiScadenzaDAO = tipiScadenzaDAO;
    }

    @Override
    public void delete(Oneritipirateizzazione entity) {

	oneritipirateizzazioneDAO.delete(entity);
    }

    @Override
    public List<Oneritipirateizzazione> findAll(Integer firstResult, Integer maxResult) {

	return oneritipirateizzazioneDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public Oneritipirateizzazione findById(PkId id) {

	return oneritipirateizzazioneDAO.findById(id);
    }

    @Override
    public void insert(Oneritipirateizzazione entity) {

	if (validateEntity(entity)) {
	    dataIntegration(entity);
	    oneritipirateizzazioneDAO.insert(entity);
	}
    }

    @Override
    public void update(Oneritipirateizzazione entity) {

	if (validateEntity(entity)) {
	    dataIntegration(entity);
	    oneritipirateizzazioneDAO.update(entity);
	}
    }

    private void dataIntegration(Oneritipirateizzazione entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro Oneritipirateizzazione da validare è nullo");
	}
	if (StringUtils.isNotBlank(entity.getTipologiaRateizzazione())) {
	    entity.setTipologiaRateizzazione(WebConstants.TIPO_RATEIZZAZIONE_DEFAULT);
	}
    }

    @Override
    protected Class<Oneritipirateizzazione> getEntityClass() {

	return Oneritipirateizzazione.class;
    }

    @Override
    public List<Oneritipirateizzazione> findAllSenzaInteressiLegali() {

	return oneritipirateizzazioneDAO.findAllSenzaInteressiLegali();
    }

    @Override
    public List<Oneritipirateizzazione> findByTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult) {

	if (StringUtils.isBlank(tipomovimento)) {
	    throw new IllegalArgumentException("Il parametro tipomovimento non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("tipimovimentoId", tipomovimento, String.class));
	ft.addRestriction(criterio);
	ft.addOrder(FilterUtils.orderAsc("ordine", "software"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "software"));
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	List<Oneritipirateizzazione> list = oneritipirateizzazioneDAO.findByFilterTable(ft, firstResult, maxResult);
	return list;
    }

    @Override
    public List<OneriTipiRateizzazioneListBean> findAllByIdcomuneSoftware() {

	return this.oneritipirateizzazioneDAO.findAllByIdcomuneSoftware(ORMHelper.getIdcomune(), ORMHelper.getSoftware());
    }

    @Override
    public List<TipoScadenzaBean> getTipiScadenzaConsentiti() {

	return this.tipiScadenzaDAO.findTipiScadenzaConsentiti();
    }

    @Override
    public OneriTipiRateizzazioneBean findById(int id) {

	return this.oneritipirateizzazioneDAO.findById(id);
    }

    @Override
    public void update(OneriTipiRateizzazioneBean tipirateizzazione) {

	Oneritipirateizzazione entity = new Oneritipirateizzazione();
	entity.setScadenzarate(null);
	entity.setTipimovimento(null);
	entity.setTipoAnatocismo(null);
	entity.setId(new PkId(tipirateizzazione.getId()));
	entity.setDescrizione(tipirateizzazione.getDescrizione());
	entity.setDetermdatainiziorate(tipirateizzazione.getDetermDataInizioRate());
	entity.setFlagInteressiLegali(tipirateizzazione.getInteressiLegali());
	if (Boolean.TRUE.equals(tipirateizzazione.getInteressiLegali())) {
	    entity.setTipoAnatocismo(tipirateizzazione.getTipoAnatocismo());
	}
	entity.setFrequenzarate(tipirateizzazione.getFrequenzaRate());
	entity.setInteressirate(tipirateizzazione.getInteressi());
	if (tipirateizzazione.getNumeroRate() != null) {
	    entity.setNrorate(tipirateizzazione.getNumeroRate().toString());
	}
	entity.setRipartizionerate(tipirateizzazione.getRipartizioneRate());
	if (tipirateizzazione.getIdScadenzaRate() != null) {
	    TipiScadenza tipoScadenza = new TipiScadenza();
	    tipoScadenza.setId(tipirateizzazione.getIdScadenzaRate());
	    entity.setScadenzarate(tipoScadenza);
	}
	entity.setSoftware(new Software());
	entity.setSpeseRateizzazione(tipirateizzazione.getSpeseRateizzazione());
	if (StringUtils.isNotBlank(tipirateizzazione.getTipoMovimento())) {
	    Tipimovimento movimento = new Tipimovimento();
	    movimento.setId(new TipimovimentoId(ORMHelper.getIdcomune(), tipirateizzazione.getTipoMovimento()));
	    entity.setTipimovimento(movimento);
	}
	entity.setTipologiaRateizzazione(tipirateizzazione.getTipologiaRateizzazione());
	entity.setScadenzePeriodi(tipirateizzazione.getScadenzePeriodi());
	this.oneritipirateizzazioneDAO.update(entity);
    }

    @Override
    public void delete(Integer id) {

	if (id == null) {
	    throw new IllegalArgumentException("Impossibile cancellare la tipologia di rateizzazione senza indicare l'id");
	}
	Oneritipirateizzazione entity = this.oneritipirateizzazioneDAO.findById(new PkId(id));
	this.oneritipirateizzazioneDAO.delete(entity);
    }

    @Override
    public List<Integer> findAllCodici() {

	List<Integer> codici = new ArrayList<Integer>();
	List<Oneritipirateizzazione> findAll = this.findAll(null, null);
	for (Oneritipirateizzazione oneritipirateizzazione : findAll) {
	    codici.add(oneritipirateizzazione.getId().getCodice());
	}
	return codici;
    }

    @Override
    public void insert(OneriTipiRateizzazioneBean tipirateizzazione) {

	Oneritipirateizzazione entity = new Oneritipirateizzazione();
	entity.setScadenzarate(null);
	entity.setTipimovimento(null);
	entity.setTipoAnatocismo(null);
	entity.setDescrizione(tipirateizzazione.getDescrizione());
	entity.setDetermdatainiziorate(tipirateizzazione.getDetermDataInizioRate());
	entity.setFlagInteressiLegali(tipirateizzazione.getInteressiLegali());
	if (Boolean.TRUE.equals(tipirateizzazione.getInteressiLegali())) {
	    entity.setTipoAnatocismo(tipirateizzazione.getTipoAnatocismo());
	}
	entity.setFrequenzarate(tipirateizzazione.getFrequenzaRate());
	entity.setInteressirate(tipirateizzazione.getInteressi());
	if (tipirateizzazione.getNumeroRate() != null) {
	    entity.setNrorate(tipirateizzazione.getNumeroRate().toString());
	}
	entity.setRipartizionerate(tipirateizzazione.getRipartizioneRate());
	if (tipirateizzazione.getIdScadenzaRate() != null) {
	    TipiScadenza tipoScadenza = new TipiScadenza();
	    tipoScadenza.setId(tipirateizzazione.getIdScadenzaRate());
	    entity.setScadenzarate(tipoScadenza);
	}
	entity.setSoftware(new Software());
	entity.setSpeseRateizzazione(tipirateizzazione.getSpeseRateizzazione());
	if (StringUtils.isNotBlank(tipirateizzazione.getTipoMovimento())) {
	    Tipimovimento movimento = new Tipimovimento();
	    movimento.setId(new TipimovimentoId(ORMHelper.getIdcomune(), tipirateizzazione.getTipoMovimento()));
	    entity.setTipimovimento(movimento);
	}
	entity.setTipologiaRateizzazione(tipirateizzazione.getTipologiaRateizzazione());
	entity.setScadenzePeriodi(tipirateizzazione.getScadenzePeriodi());
	this.oneritipirateizzazioneDAO.insert(entity);
	tipirateizzazione.setId(entity.getId().getCodice());
    }
}
