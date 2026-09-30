package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.FormegiuridicheDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Formegiuridiche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RiFormegiuridiche;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.FormegiuridicheService;
import it.gruppoinit.pal.gp.core.service.RiFormegiuridicheService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 * 
 */
@Service
public class FormegiuridicheServiceImpl extends BaseServiceImpl<Formegiuridiche, PkId> implements FormegiuridicheService {

    private FormegiuridicheDAO formegiuridicheDAO;
    private RiFormegiuridicheService riFormegiuridicheService;
    private AnagrafeService anagrafeService;

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setFormegiuridicheDAO(FormegiuridicheDAO formegiuridicheDAO) {

	this.formegiuridicheDAO = formegiuridicheDAO;
    }

    @Autowired
    public void setRiFormegiuridicheService(RiFormegiuridicheService riFormegiuridicheService) {

	this.riFormegiuridicheService = riFormegiuridicheService;
    }

    @Override
    protected Class<Formegiuridiche> getEntityClass() {

	return Formegiuridiche.class;
    }

    @Override
    public void delete(Formegiuridiche entity) {

	if (isDeleteAllowed(entity))
	    formegiuridicheDAO.delete(entity);
    }

    @Override
    public List<Formegiuridiche> findAll(Integer firstResult, Integer maxResult) {

	return formegiuridicheDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Formegiuridiche findById(PkId id) {

	return formegiuridicheDAO.findById(id);
    }

    @Override
    public void insert(Formegiuridiche entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    formegiuridicheDAO.insert(entity);
	}
    }

    @Override
    public void update(Formegiuridiche entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    formegiuridicheDAO.update(entity);
	}
    }

    private void dataIntegration(Formegiuridiche entity) {

	if (entity == null) {
	    throw new RuntimeException("L'oggetto formegiuridiche non può essere vuoto in caso di inserimento/aggiornamento");
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Formegiuridiche entity) {

	RiFormegiuridiche fg = riFormegiuridicheService.bindDomainObject(entity.getRiFormegiuridiche(), String.class, "codice");
	entity.setRiFormegiuridiche(fg);
    }

    @Override
    public List<Formegiuridiche> findByFilterTable(FilterTable filterTable) {

	return formegiuridicheDAO.findByFilterTable(filterTable);
    }

    protected boolean isDeleteAllowed(Formegiuridiche entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	List<Anagrafe> anagrafes = anagrafeService.findByFormegiuridiche(entity.getId().getCodice(), 0, 2);
	if (!anagrafes.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ANAGRAFE", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    protected Formegiuridiche customBindDomainObject(Formegiuridiche entity) {

	if (entity == null) {
	    return null;
	}
	if (StringUtils.isBlank(entity.getFormagiuridica())) {
	    return null;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("formagiuridica", entity.getFormagiuridica()));
	ft.addRestriction(fr);
	List<Formegiuridiche> results = formegiuridicheDAO.findByFilterTable(ft);
	if (results.size() == 1) {
	    return results.get(0);
	}
	return null;
    }

    @Override
    public Formegiuridiche findByCodiceRiFormegiuridiche(String riFormegiuridicheCodice) {

	if (StringUtils.isBlank(riFormegiuridicheCodice)) {
	    return null;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("riFormegiuridiche.codice", riFormegiuridicheCodice, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("id.codice"));
	List<Formegiuridiche> results = formegiuridicheDAO.findByFilterTable(ft, 0, 2);
	if (results.size() > 0) {
	    return results.get(0);
	}
	return null;
    }

    @Override
    public Formegiuridiche findByDescrizione(String formagiuridica) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("formagiuridica", formagiuridica));
	ft.addRestriction(fr);
	List<Formegiuridiche> results = formegiuridicheDAO.findByFilterTable(ft, 0, 1);
	if (results.size() > 0) {
	    return results.get(0);
	}
	return null;
    }
}
