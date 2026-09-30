package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.audit.Loggable;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.BollCfgTipoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ImplementazioniEnum;
import it.gruppoinit.pal.gp.core.dao.helper.PeriodiEnum;
import it.gruppoinit.pal.gp.core.dao.helper.TitolaritaPagamentiEnum;
import it.gruppoinit.pal.gp.core.domain.BollCfgCausalioneri;
import it.gruppoinit.pal.gp.core.domain.BollCfgMercati;
import it.gruppoinit.pal.gp.core.domain.BollCfgRuoli;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipoMetadati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CreazioneBollCfgTipo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollGestTestataDAO;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.BollCfgTipoMetadatiService;
import it.gruppoinit.pal.gp.core.service.BollCfgCausalioneriService;
import it.gruppoinit.pal.gp.core.service.BollCfgMercatiService;
import it.gruppoinit.pal.gp.core.service.BollCfgRuoliService;
import it.gruppoinit.pal.gp.core.service.BollCfgTipoService;

/**
 * 
 * @author
 */
@Loggable(featureName = "bollettazione")
@Service
public class BollCfgTipoServiceImpl extends BaseServiceImpl<BollCfgTipo, PkId> implements BollCfgTipoService {

    private BollCfgTipoDAO bollcfgtipoDAO;
    private BollCfgCausalioneriService bollCfgCausalioneriService;
    private BollCfgMercatiService bollCfgMercatiService;
    private BollCfgRuoliService bollCfgRuoliService;
    private BollGestTestataDAO bollGestTestataDAO;
    private BollCfgTipoMetadatiService bollCfgTipoMetadatiService;

    @Autowired
    public void setBollGestTestataDAO(BollGestTestataDAO bollGestTestataDAO) {

	this.bollGestTestataDAO = bollGestTestataDAO;
    }

    @Autowired
    public void setBollCfgCausalioneriService(BollCfgCausalioneriService bollCfgCausalioneriService) {

	this.bollCfgCausalioneriService = bollCfgCausalioneriService;
    }

    @Autowired
    public void setBollCfgMercatiService(BollCfgMercatiService bollCfgMercatiService) {

	this.bollCfgMercatiService = bollCfgMercatiService;
    }

    @Autowired
    public void setBollCfgRuoliService(BollCfgRuoliService bollCfgRuoliService) {

	this.bollCfgRuoliService = bollCfgRuoliService;
    }

    @Autowired
    public void setBollCfgTipoDAO(BollCfgTipoDAO bollcfgtipoDAO) {

	this.bollcfgtipoDAO = bollcfgtipoDAO;
    }

    @Autowired
    public void setBollCfgTipoMetadatiService(BollCfgTipoMetadatiService bollCfgTipoMetadatiService) {

	this.bollCfgTipoMetadatiService = bollCfgTipoMetadatiService;
    }

    @Override
    protected Class<BollCfgTipo> getEntityClass() {

	return BollCfgTipo.class;
    }

    @Override
    public List<BollCfgTipo> findAll(Integer firstResult, Integer maxResult) {

	return bollcfgtipoDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public void insert(BollCfgTipo entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    bollcfgtipoDAO.insert(entity);
	}
    }

    private void dataIntegration(BollCfgTipo entity) {

	if (entity != null) {
	    if (entity.getFlagConguaglio() == null) {
		entity.setFlagConguaglio(Boolean.FALSE);
	    }
	    if (entity.getFlagRaggruppaUtenza() == null) {
		entity.setFlagRaggruppaUtenza(Boolean.FALSE);
	    }
	    if (entity.getFlagIgnorasubentri() == null) {
		entity.setFlagIgnorasubentri(Boolean.FALSE);
	    }
	    if (ImplementazioniEnum.ISTANZE.getValore().equalsIgnoreCase(entity.getImplementazione())) {
		entity.setFlagIgnorasubentri(Boolean.FALSE);
		entity.setTitolaritaPagamenti(null);
	    }
	    if (entity.getFlagRichiestaFattura() == null) {
		entity.setFlagRichiestaFattura(Boolean.FALSE);
	    }
	    if (entity.getFlagCaricamentoMassivo() == null) {
		entity.setFlagCaricamentoMassivo(Boolean.FALSE);
	    }
	}
    }

    @Override
    public BollCfgTipo findById(PkId id) {

	return bollcfgtipoDAO.findById(id);
    }

    @Override
    public void update(BollCfgTipo entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    bollcfgtipoDAO.update(entity);
	}
    }

    @Override
    public void delete(BollCfgTipo entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    bollcfgtipoDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(BollCfgTipo entity) {

	List<BollCfgRuoli> findByBollcfgTipo = bollCfgRuoliService.findByBollcfgTipo(entity.getId().getCodice(), null, null);
	for (BollCfgRuoli bollCfgRuoli : findByBollcfgTipo) {
	    bollCfgRuoliService.delete(bollCfgRuoli);
	}
	List<BollCfgMercati> findByBollmerfc = bollCfgMercatiService.findByBollcfgTipo(entity.getId().getCodice(), null, null);
	for (BollCfgMercati merc : findByBollmerfc) {
	    bollCfgMercatiService.delete(merc);
	}
	List<BollCfgCausalioneri> cos = bollCfgCausalioneriService.findByBollCfgTipo(entity.getId().getCodice(), null, null);
	for (BollCfgCausalioneri bollCfgCausalioneri : cos) {
	    bollCfgCausalioneriService.delete(bollCfgCausalioneri);
	}
	List<BollCfgTipoMetadati> metadati = this.bollCfgTipoMetadatiService.findByBollCfgTipo(entity.getId().getCodice(), null, null);
	for (BollCfgTipoMetadati metadato : metadati) {
	    this.bollCfgTipoMetadatiService.delete(metadato);
	}
    }

    protected boolean isDeleteAllowed(BollCfgTipo entity) {

	boolean delete = true;
	// int conta = calcoloBollettazioneService.countByTipoBollettazione(entity.getId().getCodice());
	int conta = this.bollGestTestataDAO.countByTipoBollettazione(entity.getId().getCodice());
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (conta > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "BOLL_GEST_TESTATA", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<String> selectPeriodo() {

	PeriodiEnum[] list = PeriodiEnum.values();
	List<String> strings = new ArrayList<String>();
	for (int i = 0; i < list.length; i++) {
	    strings.add(list[i].getValore());
	}
	return strings;
    }

    @Override
    public List<String> selectImplementazione() {

	ImplementazioniEnum[] list = ImplementazioniEnum.values();
	List<String> strings = new ArrayList<String>();
	for (int i = 0; i < list.length; i++) {
	    strings.add(list[i].getValore());
	}
	return strings;
    }

    @Override
    public List<CreazioneBollCfgTipo> findByResponsabile(Integer codiceResponsabile) {

	return bollcfgtipoDAO.findByResponsabile(codiceResponsabile);
    }

    @Override
    public ImplementazioniEnum findImplementazioneByTipo(Integer codiceTipo) throws IllegalArgumentException {

	if (codiceTipo == null) {
	    throw new IllegalArgumentException("Nessun codice trovato.");
	}
	BollCfgTipo bollCfgTipo = bollcfgtipoDAO.findById(new PkId(codiceTipo));
	if (bollCfgTipo == null) {
	    throw new IllegalArgumentException("Nessun dato trovato per l'identificativo generico.");
	}
	return ImplementazioniEnum.fromValore(bollCfgTipo.getImplementazione());
    }

    @Override
    public PeriodiEnum findPeriodiByTipo(Integer codiceTipo) {

	if (codiceTipo == null) {
	    throw new IllegalArgumentException("Nessun codice trovato.");
	}
	BollCfgTipo bollCfgTipo = bollcfgtipoDAO.findById(new PkId(codiceTipo));
	if (bollCfgTipo == null) {
	    throw new IllegalArgumentException("Nessun dato trovato per l'identificativo generico.");
	}
	return PeriodiEnum.fromValue(bollCfgTipo.getPeriodo());
    }

    @Override
    public List<CodiceDescrizioneBean> selectTitolaritaPagamenti() {

	TitolaritaPagamentiEnum[] list = TitolaritaPagamentiEnum.values();
	List<CodiceDescrizioneBean> strings = new ArrayList<CodiceDescrizioneBean>();
	for (int i = 0; i < list.length; i++) {
	    CodiceDescrizioneBean cbd = new CodiceDescrizioneBean(list[i].name(), list[i].getValore());
	    strings.add(cbd);
	}
	return strings;
    }
}
