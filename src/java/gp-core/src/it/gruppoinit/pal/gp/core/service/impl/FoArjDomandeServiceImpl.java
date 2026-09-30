package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoArjDomandeDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoArjDomande;
import it.gruppoinit.pal.gp.core.domain.FoArjDomandeOneri;
import it.gruppoinit.pal.gp.core.domain.FoArjDomandeStepsEseguiti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FoArjDomandeOneriService;
import it.gruppoinit.pal.gp.core.service.FoArjDomandeService;
import it.gruppoinit.pal.gp.core.service.FoArjDomandeStepsEseguitiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoArjDomandeServiceImpl extends BaseServiceImpl<FoArjDomande, PkId> implements FoArjDomandeService {

    @Autowired
    private UserSecurityService userSecurityService;
    private FoArjDomandeDAO foArjDomandeDAO;
    private FoArjDomandeOneriService foArjDomandeOneriService;
    private FoArjDomandeStepsEseguitiService foArjDomandeStepsEseguitiService;

    @Autowired
    public void setFoArjDomandeDAO(FoArjDomandeDAO foArjDomandeDAO) {

	this.foArjDomandeDAO = foArjDomandeDAO;
    }

    @Autowired
    public void setFoArjDomandeOneriService(FoArjDomandeOneriService foArjDomandeOneriService) {

	this.foArjDomandeOneriService = foArjDomandeOneriService;
    }

    @Autowired
    public void setFoArjDomandeStepsEseguitiService(FoArjDomandeStepsEseguitiService foArjDomandeStepsEseguitiService) {

	this.foArjDomandeStepsEseguitiService = foArjDomandeStepsEseguitiService;
    }

    @Override
    public List<FoArjDomande> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException("Il metodo non è implementato");
    }

    @Override
    public List<FoArjDomande> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	return foArjDomandeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    protected Class<FoArjDomande> getEntityClass() {

	return FoArjDomande.class;
    }

    @Override
    public void delete(FoArjDomande entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    foArjDomandeDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(FoArjDomande entity) {

	List<FoArjDomandeOneri> oneris = foArjDomandeOneriService.findByIdDomanda(entity.getId().getCodice());
	for (FoArjDomandeOneri foArjDomandeOneri : oneris) {
	    foArjDomandeOneriService.delete(foArjDomandeOneri);
	}
	List<FoArjDomandeStepsEseguiti> stepsEseguiti = foArjDomandeStepsEseguitiService.findByFoArjDomande(entity);
	foArjDomandeStepsEseguitiService.deleteAll(stepsEseguiti);
    }

    @Override
    public FoArjDomande findById(PkId arg0) {

	return foArjDomandeDAO.findById(arg0);
    }

    @Override
    public void insert(FoArjDomande entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    foArjDomandeDAO.insert(entity);
	}
    }

    private void dataIntegration(FoArjDomande entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro FoArjDomande entity è obbligatorio");
	}
	if (entity.getFlagInvalidata() == null) {
	    entity.setFlagInvalidata(Boolean.FALSE);
	}
    }

    @Override
    public void update(FoArjDomande entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    foArjDomandeDAO.update(entity);
	}
    }

    @Override
    public void evict(FoArjDomande foArjDomande) {

	foArjDomandeDAO.evict(foArjDomande);
    }

    @Override
    public List<FoArjDomande> findDomandePerSoftware(String software, Integer firstResult, Integer maxResult) {

	FilterTable filterTable = null;
	if (StringUtils.isBlank(software)) {
	    filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	} else {
	    filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction softwareR = new FilterRestriction();
	    softwareR.addFilterField(FilterUtils.equals("software.codice", software, String.class));
	    filterTable.addRestriction(softwareR);
	}
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField((FilterUtils.isNull("dataInvio")));
	filterTable.addRestriction(restriction);
	FilterRestriction inv = new FilterRestriction();
	inv.setAndOrRestriction(AndOrRestriction.OR);
	inv.addFilterField(FilterUtils.equals("flagInvalidata", Boolean.FALSE, Boolean.class));
	inv.addFilterField(FilterUtils.isNull("flagInvalidata"));
	filterTable.addRestriction(inv);
	filterTable.addOrder(FilterUtils.orderDesc("dataUltimaModifica"));
	filterTable.addOrder(FilterUtils.orderDesc("id.codice"));
	return this.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<FoArjDomande> findDomandePerUtente(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.isNull("dataInvio"));
	restriction.addFilterField(FilterUtils.equals("anagrafeId", codiceAnagrafe, Integer.class));
	filterTable.addRestriction(restriction);
	filterTable.addOrder(FilterUtils.orderDesc("dataUltimaModifica"));
	filterTable.addOrder(FilterUtils.orderDesc("id.codice"));
	return this.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<FoArjDomande> findDomandePerUtenteEServizio(Integer codiceAnagrafe, Integer codiceServizio, Integer firstResult, Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.isNull("dataInvio"));
	restriction.addFilterField(FilterUtils.equals("anagrafeId", codiceAnagrafe, Integer.class));
	restriction.addFilterField(FilterUtils.equals("foArjServiziId", codiceServizio, Integer.class));
	filterTable.addRestriction(restriction);
	filterTable.addOrder(FilterUtils.orderDesc("dataUltimaModifica"));
	filterTable.addOrder(FilterUtils.orderDesc("id.codice"));
	return this.findByFilterTable(filterTable, firstResult, maxResult);
    }
}
