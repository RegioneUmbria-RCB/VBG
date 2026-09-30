package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.FoArjStepsTestataDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoArconfigurazione;
import it.gruppoinit.pal.gp.core.domain.FoArconfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.FoArjSteps;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.FoArconfigurazioneService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsTestataService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author fabrizioc
 */
@Service
public class FoArjStepsTestataServiceImpl extends BaseServiceImpl<FoArjStepsTestata, PkId> implements FoArjStepsTestataService {

    private static final Logger log = LoggerFactory.getLogger(FoArjStepsTestataServiceImpl.class);
    private FoArjStepsService foArjStepsService;
    private FoArjStepsTestataDAO foarjstepstestataDAO;
    private FoArconfigurazioneService foArconfigurazioneService;
    private AlberoprocService alberoprocService;

    @Autowired
    public void setFoArconfigurazioneService(FoArconfigurazioneService foArconfigurazioneService) {

	this.foArconfigurazioneService = foArconfigurazioneService;
    }

    @Autowired
    public void setFoArjStepsService(FoArjStepsService foArjStepsService) {

	this.foArjStepsService = foArjStepsService;
    }

    @Autowired
    public void setFoArjStepsTestataDAO(FoArjStepsTestataDAO foarjstepstestataDAO) {

	this.foarjstepstestataDAO = foarjstepstestataDAO;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Override
    protected Class<FoArjStepsTestata> getEntityClass() {

	return FoArjStepsTestata.class;
    }

    @Override
    public List<FoArjStepsTestata> findAll(Integer firstResult, Integer maxResult) {

	return foarjstepstestataDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoArjStepsTestata entity) {

	if (validateEntity(entity)) {
	    foarjstepstestataDAO.insert(entity);
	}
    }

    @Override
    public FoArjStepsTestata findById(PkId id) {

	return foarjstepstestataDAO.findById(id);
    }

    @Override
    public void update(FoArjStepsTestata entity) {

	if (validateEntity(entity)) {
	    foarjstepstestataDAO.update(entity);
	}
    }

    @Override
    public void delete(FoArjStepsTestata entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    foarjstepstestataDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(FoArjStepsTestata entity) {

	List<FoArjSteps> list = foArjStepsService.findByTestata(entity.getId().getIdcomune(), entity.getId().getCodice());
	for (FoArjSteps foArjSteps : list) {
	    foArjStepsService.delete(foArjSteps);
	}
    }

    protected boolean isDeleteAllowed(FoArjStepsTestata entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (foArconfigurazioneService.findByFoArjStepsTestata(entity.getId().getCodice()).size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "FO_ARCONFIGURAZIONE", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<FoArjStepsTestata> findByDescrizione(String textToSearch) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.like("descrizione", textToSearch));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return foarjstepstestataDAO.findByFilterTable(ft);
    }

    @Override
    public FoArjStepsTestata findTestataDefault() throws Exception {

	FoArjStepsTestata testata = null;
	FoArconfigurazioneId id = new FoArconfigurazioneId();
	FoArconfigurazione foArConf = foArconfigurazioneService.findById(id);
	if (foArConf == null) {
	    log.error("findTestataDefault(): FoArconfigurazione non trovata per i parametri [idcomune={}, software={}]", id.getIdcomune(),
		    id.getSoftware());
	    throw new Exception("Testata Default non trovata");
	}
	testata = foArConf.getFoArjStepsTestata();
	if (testata == null) {
	    log.error("findTestataDefault(): FoArjStepsTestata nullo per la FoArconfigurazione caricata [idcomune={}, software={}]",
		    id.getIdcomune(), id.getSoftware());
	    throw new Exception("Testata Default non trovata");
	}
	return testata;
    }

    @Override
    public FoArjStepsTestata findTestataIntervento(String idcomune, Integer codiceIntervento) throws Exception {

	FoArjStepsTestata testata = null;
	if (codiceIntervento == null) {
	    log.error("findTestataIntervento(codiceIntervento=[{}]): codice intervento obbligatorio", codiceIntervento);
	    throw new Exception("Testata Intervento non trovata");
	}
	testata = alberoprocService.findFoArjStepsTestata(idcomune, codiceIntervento);
	return testata;
    }
}
