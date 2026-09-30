package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.FaqDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.Faqclassi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FaqService;
import it.gruppoinit.pal.gp.core.service.FaqclassiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Riccardo Bocci
 */
@Service
public class FaqServiceImpl extends BaseServiceImpl<Faq, PkId> implements FaqService {

    private FaqDAO faqDAO;
    private SoftwareService softwareService;
    private FaqclassiService faqclassiService;

    @Autowired
    public void setFaqDAO(FaqDAO faqDAO) {

	this.faqDAO = faqDAO;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setFaqclassiService(FaqclassiService faqclassiService) {

	this.faqclassiService = faqclassiService;
    }

    @Override
    protected Class<Faq> getEntityClass() {

	return Faq.class;
    }

    @Override
    public List<Faq> findAll(Integer firstResult, Integer maxResult) {

	return faqDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Faq entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    faqDAO.insert(entity);
	}
    }

    @Override
    public Faq findById(PkId id) {

	return faqDAO.findById(id);
    }

    @Override
    public void update(Faq entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    faqDAO.update(entity);
	}
    }

    @Override
    public void delete(Faq entity) {

	if (isDeleteAllowed(entity)) {
	    faqDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Faq entity) {

	boolean delete = true;
	return delete;
    }

    @Override
    public List<Faq> findByFilter(List<String> softwareList, Integer firstResult, Integer maxResult) {

	return faqDAO.findByFilter(softwareList, firstResult, maxResult);
    }

    private void dataIntegration(Faq entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("La Faq passata è nulla");
	}
	fixMergeEntityProperties(entity);
	if (entity.getPubblicare() == null) {
	    entity.setPubblicare(Boolean.TRUE);
	}
    }

    protected void fixMergeEntityProperties(Faq entity) {

	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
	Faqclassi faqclassi = faqclassiService.bindDomainObject(entity.getFaqclassi(), PkId.class, "id.codice");
	entity.setFaqclassi(faqclassi);
    }

    @Override
    public List<Faq> findAllPubblicate(Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("pubblicare", Boolean.TRUE, Boolean.class));
	ft.addRestriction(fr);
	FilterRestriction soft = new FilterRestriction();
	soft.addFilterField(FilterUtils.in("software.codice", new String[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }, String.class));
	ft.addRestriction(soft);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("domanda"));
	return faqDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<Faq> findByFaqClassi(Integer codiceFaqclassi, Boolean isPubblica, String software, boolean isCercaPerTT) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (codiceFaqclassi != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", codiceFaqclassi, "faqclassi", Boolean.class));
	} else {
	    fr.addFilterField(FilterUtils.isNull("id.codice", "faqclassi"));
	}
	if (isPubblica != null) {
	    fr.addFilterField(FilterUtils.equals("pubblicare", isPubblica, Boolean.class));
	}
	if (!isCercaPerTT) {
	    fr.addFilterField(FilterUtils.equals("codice", software, "software", String.class));
	} else {
	    fr.addFilterField(FilterUtils.in("codice", new String[] { software, WebConstants.SOFTWARE_TT }, "software", String.class));
	}
	ft.addRestriction(fr);
	//	FilterRestriction soft = new FilterRestriction();
	//	soft.addFilterField(FilterUtils.in("software.codice", new String[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }, String.class));
	//	ft.addRestriction(soft);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("domanda"));
	return faqDAO.findByFilterTable(ft);
    }

    @Override
    public List<Faq> findWithoutFaqClassi(Boolean isPubblica, String software, boolean isCercaPerTT) {

	return this.findByFaqClassi(null, isPubblica, software, isCercaPerTT);
    }
}
