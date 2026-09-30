package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoArjDomandeStepsEseguitiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoArjDomande;
import it.gruppoinit.pal.gp.core.domain.FoArjDomandeStepsEseguiti;
import it.gruppoinit.pal.gp.core.domain.FoArjSteps;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FoArjDomandeStepsEseguitiService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author fabrizioc
 */
@Service
public class FoArjDomandeStepsEseguitiServiceImpl extends BaseServiceImpl<FoArjDomandeStepsEseguiti, PkId> implements
	FoArjDomandeStepsEseguitiService {

    private static final Logger log = LoggerFactory.getLogger(FoArjDomandeStepsEseguitiServiceImpl.class);
    private FoArjDomandeStepsEseguitiDAO foarjdomandestepseseguitiDAO;

    @Autowired
    public void setFoArjDomandeStepsEseguitiDAO(FoArjDomandeStepsEseguitiDAO foarjdomandestepseseguitiDAO) {

	this.foarjdomandestepseseguitiDAO = foarjdomandestepseseguitiDAO;
    }

    @Override
    protected Class<FoArjDomandeStepsEseguiti> getEntityClass() {

	return FoArjDomandeStepsEseguiti.class;
    }

    @Override
    public List<FoArjDomandeStepsEseguiti> findByFoArjDomande(FoArjDomande foArjDomande) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("foArjDomandeId", foArjDomande.getId().getCodice(), Integer.class));
	filterTable.addRestriction(restriction);
	return foarjdomandestepseseguitiDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<FoArjDomandeStepsEseguiti> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public void insert(FoArjDomandeStepsEseguiti entity) {

	if (validateEntity(entity)) {
	    foarjdomandestepseseguitiDAO.insert(entity);
	}
    }

    @Override
    public FoArjDomandeStepsEseguiti findById(PkId id) {

	return foarjdomandestepseseguitiDAO.findById(id);
    }

    @Override
    public void update(FoArjDomandeStepsEseguiti entity) {

	if (validateEntity(entity)) {
	    foarjdomandestepseseguitiDAO.update(entity);
	}
    }

    @Override
    public void deleteAll(List<FoArjDomandeStepsEseguiti> stepsToDelete) {

	log.debug("deleteAll");
	for (FoArjDomandeStepsEseguiti stepToDelete : stepsToDelete) {
	    this.delete(stepToDelete);
	}
    }

    @Override
    public void delete(FoArjDomandeStepsEseguiti entity) {

	if (isDeleteAllowed(entity)) {
	    foarjdomandestepseseguitiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(FoArjDomandeStepsEseguiti entity) {

	return true;
    }

    @Override
    public void insertEseguito(FoArjDomande foArjDomande, FoArjSteps currentStep) {

	log.debug("insertEseguito");
	boolean insert = true;
	for (FoArjDomandeStepsEseguiti stepEeguito : foArjDomande.getFoArjDomandeStepsEseguitis()) {
	    if (currentStep.getFoArjStepsBase().getNomeStep().equals(stepEeguito.getFoArjStepsBase().getNomeStep())) {
		insert = false;
		break;
	    }
	}
	if (insert) {
	    log.debug("insertEseguito: insert");
	    FoArjDomandeStepsEseguiti stepEseguitoDaInserire = new FoArjDomandeStepsEseguiti();
	    stepEseguitoDaInserire.setFoArjDomande(foArjDomande);
	    stepEseguitoDaInserire.setFoArjStepsBase(currentStep.getFoArjStepsBase());
	    this.insert(stepEseguitoDaInserire);
	}
    }
}
