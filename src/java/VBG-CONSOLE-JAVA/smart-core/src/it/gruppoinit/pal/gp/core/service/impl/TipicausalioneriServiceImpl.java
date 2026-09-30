/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipicausalioneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocOneri;
import it.gruppoinit.pal.gp.core.domain.CanoniConfigurazione;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.LavoritipiCausalioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioninteressi;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.RaggruppamentocausalioneriService;
import it.gruppoinit.pal.gp.core.service.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.service.TipicausalioninteressiService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class TipicausalioneriServiceImpl extends BaseServiceImpl<Tipicausalioneri, PkId> implements TipicausalioneriService {

    private RaggruppamentocausalioneriService raggruppamentocausalioneriService;
    private TipicausalioneriDAO tipicausalioneriDAO;
    private TipicausalioninteressiService tipicausalioninteressiService;
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setRaggruppamentocausalioneriService(RaggruppamentocausalioneriService raggruppamentocausalioneriService) {

	this.raggruppamentocausalioneriService = raggruppamentocausalioneriService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setTipicausalioninteressiService(TipicausalioninteressiService tipicausalioninteressiService) {

	this.tipicausalioninteressiService = tipicausalioninteressiService;
    }

    @Autowired
    public void setTipicausalioneriDAO(TipicausalioneriDAO tipicausalioneriDAO) {

	this.tipicausalioneriDAO = tipicausalioneriDAO;
    }

    @Override
    protected Class<Tipicausalioneri> getEntityClass() {

	return Tipicausalioneri.class;
    }

    @Override
    public void delete(Tipicausalioneri entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    tipicausalioneriDAO.delete(entity);
	}
    }

    @Override
    public List<Tipicausalioneri> findAll(Integer firstResult, Integer maxResult) {

	return tipicausalioneriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Tipicausalioneri findById(PkId id) {

	return tipicausalioneriDAO.findById(id);
    }

    @Override
    public void insert(Tipicausalioneri entity) {

	dataintegration(entity);
	if (isInsertUpdateAllowed(entity)) {
	    if (validateEntity(entity)) {
		tipicausalioneriDAO.insert(entity);
	    }
	}
    }

    @Override
    public void update(Tipicausalioneri entity) {

	dataintegration(entity);
	if (isInsertUpdateAllowed(entity)) {
	    if (validateEntity(entity)) {
		tipicausalioneriDAO.update(entity);
	    }
	}
    }

    private void dataintegration(Tipicausalioneri entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro è nullo Tipicausalioneri");
	}
	if (entity.getCoDisabilitato() == null) {
	    entity.setCoDisabilitato(Boolean.FALSE);
	}
	if (entity.getCoSerichiedeendo() == null) {
	    entity.setCoSerichiedeendo(Boolean.FALSE);
	}
	if (entity.getFlgTipicausaliinteressi() == null) {
	    entity.setFlgTipicausaliinteressi(Boolean.FALSE);
	}
	if (entity.getPagamentiregulus() == null) {
	    entity.setPagamentiregulus(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Tipicausalioneri entity) {

	Tipicausalioneri causaleBollo = this.bindDomainObject(entity.getCausalebollo(), PkId.class, "id.codice");
	entity.setCausalebollo(causaleBollo);
	Tipicausalioneri causaliDiMora = this.bindDomainObject(entity.getTipicausalioneriMora(), PkId.class, "id.codice");
	entity.setTipicausalioneriMora(causaliDiMora);
	Raggruppamentocausalioneri raggruppamentocausalioneri = raggruppamentocausalioneriService.bindDomainObject(
		entity.getRaggruppamentocausalioneri(), PkId.class, "id.codice");
	entity.setRaggruppamentocausalioneri(raggruppamentocausalioneri);
    }

    @Override
    public List<Tipicausalioneri> findCausaliBollo(Tipicausalioneri tipicausalioneri) {

	return tipicausalioneriDAO.findCausaliBollo(tipicausalioneri);
    }

    protected boolean isInsertUpdateAllowed(Tipicausalioneri entity) {

	boolean isAllowed = true;
	Tipicausalioneri bollo = entity.getCausalebollo();
	if (bollo != null && bollo.getId() != null && bollo.getId().getCodice() != null) {
	    if (this.isCausaleBollo(bollo, entity)) {
		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
		_ivs.add(new InvalidValue("service_error.causalebollo_presente", entity.getClass(), null, null, null));
		this.throwValidationMessages(_ivs);
	    }
	}
	return isAllowed;
    }

    @Override
    public boolean isCausaleBollo(Tipicausalioneri causalebollo, Tipicausalioneri tipicausalioneri) {

	return tipicausalioneriDAO.isCausaleBollo(causalebollo, tipicausalioneri);
    }

    protected boolean isDeleteAllowed(Tipicausalioneri entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Set<AlberoprocOneri> alberoprocOneris = entity.getAlberoprocOneris();
	if (!alberoprocOneris.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ALBEROPROC_ONERI", null));
	    delete = false;
	}
	Set<CanoniConfigurazione> canoniConfiguraziones = entity.getCanoniConfiguraziones();
	if (!canoniConfiguraziones.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "CANONI_CONFIGURAZIONE", null));
	    delete = false;
	}
	Set<CanoniConfigurazione> canoniConfigurazioneOneriTotales = entity.getCanoniConfigurazioneOneriTotales();
	if (!canoniConfigurazioneOneriTotales.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "CANONI_CONFIGURAZIONE", null));
	    delete = false;
	}
	Set<CanoniConfigurazione> canoniConfigurazioneAddizcomunales = entity.getCanoniConfigurazioneAddizcomunales();
	if (!canoniConfigurazioneAddizcomunales.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "CANONI_CONFIGURAZIONE", null));
	    delete = false;
	}
	Set<LavoritipiCausalioneri> lavoritipiCausalioneris = entity.getLavoritipiCausalioneris();
	if (!lavoritipiCausalioneris.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "LAVORITIPI_CAUSALIONERI", null));
	    delete = false;
	}
	Set<Inventarioprocedimentioneri> inventarioprocedimentioneris = entity.getInventarioprocedimentioneris();
	if (!inventarioprocedimentioneris.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "INVENTARIO_PROCEDIMENTI_ONERI", null));
	    delete = false;
	}
	if (this.isCausaleBollo(entity, null)) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "TIPICAUSALIONERI", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Tipicausalioneri> findByDescrizione(String descrizione, String codiceSoftware) {

	return tipicausalioneriDAO.findByDescrizione(descrizione, codiceSoftware);
    }

    @Override
    public List<Tipicausalioneri> findByDescrizioneAndFlagEndo(String textToSearch, Boolean flagEndo, String codiceSoftware) {

	return tipicausalioneriDAO.findByDescrizioneAndFlagEndo(textToSearch, flagEndo, codiceSoftware);
    }

    @Override
    public boolean isImportoIstruttoriaImpostabile(Tipicausalioneri entity) {

	boolean impostabile = false;
	boolean isDisOneriImportoIstruttoria = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_DIS_ONERIIMPORTOISTRUTTORIA);
	boolean isVisOneriImportoIstruttoria = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_VIS_ONERIIMPORTOISTRUTTORIA);
	if (BooleanUtils.isTrue(entity.getCoSerichiedeendo())) {
	    if (isDisOneriImportoIstruttoria) {
		impostabile = false;
	    } else {
		impostabile = true;
	    }
	} else {
	    if (isVisOneriImportoIstruttoria) {
		impostabile = true;
	    } else {
		impostabile = false;
	    }
	}
	return impostabile;
    }

    @Override
    public List<Tipicausalioneri> findAll(String idcomune, Integer firstResult, Integer maxResult, boolean isInteressiDiMora) {

	return tipicausalioneriDAO.findAll(idcomune, firstResult, maxResult, isInteressiDiMora);
    }

    @Override
    public List<Tipicausalioneri> findTipicausalioneriMoraByDescrizione(String textToSearch, String codiceSoftware) {

	FilterTable ft = null;
	FilterRestriction fr = new FilterRestriction();
	if (StringUtils.isBlank(codiceSoftware)) {
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	} else {
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    fr.addFilterField(FilterUtils.equals("codice", codiceSoftware, "software", String.class));
	}
	fr.addFilterField(FilterUtils.equals("flgTipicausaliinteressi", true, Boolean.class));
	try {
	    fr.addFilterField(FilterUtils.equals("id.codice", Integer.parseInt(textToSearch), Integer.class));
	} catch (Exception e) {
	    fr.addFilterField(FilterUtils.like("coDescrizione", textToSearch));
	}
	ft.addRestriction(fr);
	return (List<Tipicausalioneri>) tipicausalioneriDAO.findByFilterTable(ft);
    }

    @Override
    public Tipicausalioneri findbByCodiceCausalepeople(String codiceCausaleStr) {

	FilterTable ft = null;
	FilterRestriction fr = new FilterRestriction();
	ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	fr.addFilterField(FilterUtils.equals("codicecausalepeople", codiceCausaleStr, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("id.codice"));
	List<Tipicausalioneri> result = (List<Tipicausalioneri>) tipicausalioneriDAO.findByFilterTable(ft);
	if (result.size() > 0) {
	    return result.get(0);
	}
	fr = new FilterRestriction();
	ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	fr.addFilterField(FilterUtils.equals("software.codice", WebConstants.SOFTWARE_TT, String.class));
	fr.addFilterField(FilterUtils.equals("codicecausalepeople", codiceCausaleStr, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("id.codice"));
	result = (List<Tipicausalioneri>) tipicausalioneriDAO.findByFilterTable(ft);
	if (result.size() > 0) {
	    return result.get(0);
	}
	return null;
    }

    @Override
    protected void childDelete(Tipicausalioneri entity) {

	if (entity.getTipicausalioninteressis() != null && !entity.getTipicausalioninteressis().isEmpty()) {
	    Set<Tipicausalioninteressi> list = entity.getTipicausalioninteressis();
	    for (Tipicausalioninteressi tipicausalioninteressi : list) {
		tipicausalioninteressiService.delete(tipicausalioninteressi);
	    }
	}
    }
}
