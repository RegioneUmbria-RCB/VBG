package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.FoArjServiziDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.FoArjServizi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.FoArjServiziService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * 
 * @author fabrizioc
 */
@Service
public class FoArjServiziServiceImpl extends BaseServiceImpl<FoArjServizi, PkId> implements FoArjServiziService {

    private AlberoprocService alberoprocService;
    private FoArjServiziDAO foarjserviziDAO;
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setFoArjServiziDAO(FoArjServiziDAO foarjserviziDAO) {

	this.foarjserviziDAO = foarjserviziDAO;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    protected Class<FoArjServizi> getEntityClass() {

	return FoArjServizi.class;
    }

    @Override
    public List<FoArjServizi> findAll(Integer firstResult, Integer maxResult) {

	return foarjserviziDAO.findAll(firstResult, maxResult);
    }

    @Override
    protected boolean validateEntity(FoArjServizi entity) {

	boolean isUrlBrevi = verticalizzazioniService.isAttivaAndParametroEqualsToValore(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA,
		WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA_CENTRO_SERVIZI_URL_BREVI, "1");
	if (!isUrlBrevi) {
	    List<FoArjServizi> list = findByAlberoproc(entity.getAlberoproc().getId().getCodice());
	    if (list.size() > 0) {
		List<InvalidValue> ivs = new ArrayList<InvalidValue>();
		InvalidValue iv = new InvalidValue("service.error.foarjservizi.record_univoco_per_intervento", entity.getClass(), "urlServizio",
			null, entity);
		ivs.add(iv);
		throwValidationMessages(ivs);
	    }
	}
	return true;
    }

    @Override
    public void insert(FoArjServizi entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    try {
		foarjserviziDAO.insert(entity);
		foarjserviziDAO.flush();
	    } catch (DataIntegrityViolationException e) {
		List<InvalidValue> ivs = new ArrayList<InvalidValue>();
		InvalidValue iv = new InvalidValue("service.error.unique.key", entity.getClass(), "urlServizio", null, entity);
		ivs.add(iv);
		throwValidationMessages(ivs);
	    }
	}
    }

    @Override
    public FoArjServizi findById(PkId id) {

	return foarjserviziDAO.findById(id);
    }

    @Override
    public void update(FoArjServizi entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    try {
		foarjserviziDAO.update(entity);
	    } catch (DataIntegrityViolationException e) {
		List<InvalidValue> ivs = new ArrayList<InvalidValue>();
		InvalidValue iv = new InvalidValue("service.error.unique.key", entity.getClass(), "urlServizio", null, entity);
		ivs.add(iv);
		throwValidationMessages(ivs);
	    }
	}
    }

    private void dataIntegration(FoArjServizi entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro entity non può essere nullo");
	}
	if (entity.getAnonimo() == null) {
	    entity.setAnonimo(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
	boolean isUrlBrevi = verticalizzazioniService.isAttivaAndParametroEqualsToValore(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA,
		WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA_CENTRO_SERVIZI_URL_BREVI, "1");
	if (!isUrlBrevi) {
	    String descrizioneServizio = "URL_SERVIZIO-";
	    if (entity.getAlberoproc() != null) {
		if (entity.getAlberoproc().getId() != null) {
		    if (entity.getAlberoproc().getId().getCodice() != null) {
			descrizioneServizio += entity.getAlberoproc().getId().getCodice();
		    }
		}
	    }
	    entity.setUrlServizio(descrizioneServizio);
	}
    }

    @Override
    protected void fixMergeEntityProperties(FoArjServizi entity) {

	Alberoproc alberoproc = alberoprocService.bindDomainObject(entity.getAlberoproc(), PkId.class, "id.codice");
	entity.setAlberoproc(alberoproc);
    }

    @Override
    public void delete(FoArjServizi entity) {

	if (isDeleteAllowed(entity)) {
	    foarjserviziDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(FoArjServizi entity) {

	//non ci sono vincoli alla cancellazione di un record
	return true;
    }

    @Override
    public List<FoArjServizi> findByAlberoproc(Integer codiceIntervento) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("alberoprocId", codiceIntervento, Integer.class));
	filterTable.addRestriction(restriction);
	List<FoArjServizi> list = foarjserviziDAO.findByFilterTable(filterTable);
	return list;
    }

    @Override
    public List<FoArjServizi> findByUrlServizio(String urlServizio) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("urlServizio", urlServizio, String.class));
	filterTable.addRestriction(restriction);
	List<FoArjServizi> list = foarjserviziDAO.findByFilterTable(filterTable);
	return list;
    }

    @Override
    public List<FoArjServizi> findBySoftware(Integer firstResult, Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), "alberoproc", String.class));
	filterTable.addRestriction(restriction);
	List<FoArjServizi> list = foarjserviziDAO.findByFilterTable(filterTable);
	return list;
    }
}