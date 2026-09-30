package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CategorieEventiMailDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.CategorieEventiMail;
import it.gruppoinit.pal.gp.core.domain.Categorieeventibase;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CategorieEventiMailService;
import it.gruppoinit.pal.gp.core.service.CategorieeventibaseService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategorieEventiMailServiceImpl extends BaseServiceImpl<CategorieEventiMail, PkId> implements CategorieEventiMailService {

    private CategorieEventiMailDAO categorieEventiMailDAO;
    private MailtipoService mailtipoService;
    private CategorieeventibaseService categorieeventibaseService;
    private MailConfigService mailConfigService;

    @Autowired
    public void setCategorieeventibaseService(CategorieeventibaseService categorieeventibaseService) {

	this.categorieeventibaseService = categorieeventibaseService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setCategorieEventiMailDAO(CategorieEventiMailDAO categorieEventiMailDAO) {

	this.categorieEventiMailDAO = categorieEventiMailDAO;
    }       
    
    @Autowired
    public void setMailConfigService(MailConfigService mailConfigService) {
    
        this.mailConfigService = mailConfigService;
    }

    @Override
    public void insert(CategorieEventiMail entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    categorieEventiMailDAO.insert(entity);
	}
    }

    @Override
    public void update(CategorieEventiMail entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    categorieEventiMailDAO.update(entity);
	}
    }

    private void dataIntegration(CategorieEventiMail entity) {

	if (entity == null) {
	    return;
	}
	if (entity.getFlagAttiva() == null) {
	    entity.setFlagAttiva(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(CategorieEventiMail entity) {

	Mailtipo mailtipo = mailtipoService.bindDomainObject(entity.getMailtipo(), PkId.class, "id.codice");
	entity.setMailtipo(mailtipo);
	
	MailConfig mailconfig = mailConfigService.bindDomainObject(entity.getMailConfig(), PkId.class, "id.codice");
	entity.setMailConfig(mailconfig);
	
	if (EntityUtils.getNestedProperty(entity.getCategorieeventibase(), "id") != null) {
	    Categorieeventibase cat = categorieeventibaseService.findById(entity.getCategorieeventibase().getId());
	    entity.setCategorieeventibase(cat);
	} else {
	    entity.setCategorieeventibase(null);
	}
    }

    @Override
    public void delete(CategorieEventiMail entity) {

	if (isDeleteAllowed(entity)) {
	    categorieEventiMailDAO.delete(entity);
	}
    }

    @Override
    public List<CategorieEventiMail> findAll(Integer firstResult, Integer maxResult) {

	return categorieEventiMailDAO.findAll(null, null, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public CategorieEventiMail findById(PkId id) {

	return categorieEventiMailDAO.findById(id);
    }

    @Override
    protected Class<CategorieEventiMail> getEntityClass() {

	return CategorieEventiMail.class;
    }

    @Override
    public List<CategorieEventiMail> findByIstanzeEventi(Istanzeeventi input) {

	List<CategorieEventiMail> result = new ArrayList<CategorieEventiMail>();
	if (input == null) {
	    return result;
	}
	if (input.getCategorieeventibase() == null) {
	    return result;
	}
	if (StringUtils.isBlank(input.getCategorieeventibase().getId())) {
	    return result;
	}
	String evento = input.getDescrizione();
	if (StringUtils.isBlank(evento)) {
	    return result;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("categorieeventibase.id", input.getCategorieeventibase().getId(), String.class));
	fr.addFilterField(FilterUtils.equals("flagAttiva", Boolean.TRUE, Boolean.class));
	ft.addRestriction(fr);
	List<CategorieEventiMail> result2 = categorieEventiMailDAO.findByFilterTable(ft);
	for (CategorieEventiMail cem : result2) {
	    String triggerType = StringUtils.defaultString(cem.getTriggerType());
	    String triggerText = StringUtils.defaultString(cem.getTriggerText());
	    if (StringUtils.isNotBlank(triggerType) && StringUtils.isNotBlank(triggerText)) {
		if (verificaTrigger(triggerType, triggerText, evento)) {
		    result.add(cem);
		}
	    }
	}
	return result;
    }

    private boolean verificaTrigger(String triggerType, String triggerText, String evento) {

	if (triggerType.equalsIgnoreCase("contains")) {
	    return evento.toLowerCase().indexOf(triggerText.toLowerCase()) >= 0;
	} else if (triggerType.equalsIgnoreCase("startsWith")) {
	    return evento.toLowerCase().startsWith(triggerText.toLowerCase());
	} else if (triggerType.equalsIgnoreCase("endsWith")) {
	    return evento.toLowerCase().endsWith(triggerText.toLowerCase());
	}
	return false;
    }
}
