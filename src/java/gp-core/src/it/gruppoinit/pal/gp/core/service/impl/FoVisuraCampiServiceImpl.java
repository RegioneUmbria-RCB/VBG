package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoVisuraCampiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoVisuraCampi;
import it.gruppoinit.pal.gp.core.domain.FoVisuraCampiBase;
import it.gruppoinit.pal.gp.core.domain.FoVisuraCampiId;
import it.gruppoinit.pal.gp.core.domain.FoVisuraContestiBase;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.FoVisuraCampiHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FoVisuraCampiBaseService;
import it.gruppoinit.pal.gp.core.service.FoVisuraCampiService;
import it.gruppoinit.pal.gp.core.service.FoVisuraContestiBaseService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class FoVisuraCampiServiceImpl extends BaseServiceImpl<FoVisuraCampi, FoVisuraCampiId> implements FoVisuraCampiService {

    private FoVisuraCampiDAO fovisuracampiDAO;
    private SoftwareService softwareService;
    private FoVisuraContestiBaseService foVisuraContestiBaseService;
    private FoVisuraCampiBaseService foVisuraCampiBaseService;

    @Autowired
    public void setFoVisuraCampiDAO(FoVisuraCampiDAO fovisuracampiDAO) {

	this.fovisuracampiDAO = fovisuracampiDAO;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setFoVisuraContestiBaseService(FoVisuraContestiBaseService foVisuraContestiBaseService) {

	this.foVisuraContestiBaseService = foVisuraContestiBaseService;
    }

    @Autowired
    public void setFoVisuraCampiBaseService(FoVisuraCampiBaseService foVisuraCampiBaseService) {

	this.foVisuraCampiBaseService = foVisuraCampiBaseService;
    }

    @Override
    protected Class<FoVisuraCampi> getEntityClass() {

	return FoVisuraCampi.class;
    }

    @Override
    public List<FoVisuraCampi> findAll(Integer firstResult, Integer maxResult) {

	return fovisuracampiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoVisuraCampi entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    fovisuracampiDAO.insert(entity);
	}
    }

    @Override
    public FoVisuraCampi findById(FoVisuraCampiId id) {

	return fovisuracampiDAO.findById(id);
    }

    @Override
    public void update(FoVisuraCampi entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    fovisuracampiDAO.update(entity);
	}
    }

    @Override
    public void delete(FoVisuraCampi entity) {

	if (isDeleteAllowed(entity)) {
	    fovisuracampiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(FoVisuraCampi entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<FoVisuraCampi> findByContestiBase(FoVisuraContestiBase foVisuraContestiBase) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction campiRestriction = new FilterRestriction();
	if (EntityUtils.getNestedProperty(foVisuraContestiBase, "id") != null) {
	    campiRestriction.addFilterField(FilterUtils.equals("id.fkidcontesto", foVisuraContestiBase.getId(), String.class));
	}
	filterTable.addRestriction(campiRestriction);
	return fovisuracampiDAO.findByFilterTable(filterTable);
    }

    @Override
    public void updateCampiFromFoVisuraCampiHelper(FoVisuraCampiHelper foVisuraCampiHelper) {

	List<FoVisuraCampi> foVisuraCampis = findByContestiBase(foVisuraCampiHelper.getContestoBase());
	for (FoVisuraCampi foVisuraCampi : foVisuraCampis) {
	    fovisuracampiDAO.delete(foVisuraCampi);
	}
	if (EntityUtils.getNestedProperty(foVisuraCampiHelper, "foVisuraCampis") != null) {
	    for (FoVisuraCampi foVisuraCampiTemp : foVisuraCampiHelper.getFoVisuraCampis()) {
		if (foVisuraCampiTemp.getPosizione() != null && foVisuraCampiTemp.getPosizione() != 0) {
		    fovisuracampiDAO.insert(foVisuraCampiTemp);
		}
	    }
	}
    }

    private void dataIntegration(FoVisuraCampi entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro FoVisuraCampi è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(FoVisuraCampi entity) {

	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
	FoVisuraContestiBase foVisuraContestiBase = foVisuraContestiBaseService
		.bindDomainObject(entity.getFoVisuraContestiBase(), String.class, "id");
	entity.setFoVisuraContestiBase(foVisuraContestiBase);
	FoVisuraCampiBase foVisuraCampiBase = foVisuraCampiBaseService.bindDomainObject(entity.getFoVisuraCampiBase(), String.class, "id");
	entity.setFoVisuraCampiBase(foVisuraCampiBase);
    }
}
