package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MercatiCategorieDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MercatiCategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MercatiCategorieService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MercatiCategorieServiceImpl extends BaseServiceImpl<MercatiCategorie, PkId> implements MercatiCategorieService {

    private MercatiCategorieDAO mercatiCategorieDAO;
    private SoftwareService softwareService;
    private MercatiService mercatiService;

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setMercatiCategorieDAO(MercatiCategorieDAO mercatiCategorieDAO) {

	this.mercatiCategorieDAO = mercatiCategorieDAO;
    }

    @Override
    public void insert(MercatiCategorie entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatiCategorieDAO.insert(entity);
	}
    }

    @Override
    public void update(MercatiCategorie entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatiCategorieDAO.update(entity);
	}
    }

    private void dataIntegration(MercatiCategorie entity) {

	if (entity.getSoftware() == null) {
	    Software software = softwareService.findById(ORMHelper.getSoftware());
	    entity.setSoftware(software);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(MercatiCategorie entity) {

	Software s = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(s);
    }

    @Override
    public void delete(MercatiCategorie entity) {

	if (isDeleteAllowed(entity)) {
	    mercatiCategorieDAO.delete(entity);
	}
    }

    @Override
    protected boolean isDeleteAllowed(MercatiCategorie entity) {

	int mercatis = mercatiService.countByCategorieMercato(entity.getId().getCodice());
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	boolean delete = true;
	if (mercatis > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "REGISTRAZIONI", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<MercatiCategorie> findAll(Integer firstResult, Integer maxResult) {

	return mercatiCategorieDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public MercatiCategorie findById(PkId id) {

	return mercatiCategorieDAO.findById(id);
    }

    @Override
    protected Class<MercatiCategorie> getEntityClass() {

	return MercatiCategorie.class;
    }

    @Override
    public List<MercatiCategorie> findByDescrizione(String textToSearch) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction a = new FilterRestriction();
	a.addFilterField(FilterUtils.like("descrizione", "%" + textToSearch + "%"));
	ft.addRestriction(a);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return mercatiCategorieDAO.findByFilterTable(ft);
    }
}
