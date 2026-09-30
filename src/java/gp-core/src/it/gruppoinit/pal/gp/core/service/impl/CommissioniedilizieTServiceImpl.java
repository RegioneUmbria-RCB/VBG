package it.gruppoinit.pal.gp.core.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.CommissioniedilizieTDAO;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAllegati;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;
import it.gruppoinit.pal.gp.core.domain.CommedilizieConvocazioni;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CommissioniTHelper;
import it.gruppoinit.pal.gp.core.domain.web.CommissioniedilizieTCommand;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.CommedilizieAllegatiService;
import it.gruppoinit.pal.gp.core.service.CommedilizieAppelloService;
import it.gruppoinit.pal.gp.core.service.CommedilizieConvocazioniService;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipologieService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieRService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieTService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author gianpaolot
 */
@Service
public class CommissioniedilizieTServiceImpl extends BaseServiceImpl<CommissioniedilizieT, PkId> implements CommissioniedilizieTService {

    private CommissioniedilizieTDAO commissioniedilizietDAO;
    private CommedilizieTipologieService commedilizieTipologieService;
    private CommissioniedilizieRService commissioniedilizieRService;
    private CommedilizieAppelloService commedilizieAppelloService;
    private CommedilizieAllegatiService commedilizieAllegatiService;
    private CommedilizieConvocazioniService commedilizieConvocazioniService;

    @Autowired
    public void setCommissioniedilizieTDAO(CommissioniedilizieTDAO commissioniedilizietDAO) {

	this.commissioniedilizietDAO = commissioniedilizietDAO;
    }

    @Autowired
    public void setCommedilizieTipologieService(CommedilizieTipologieService commedilizieTipologieService) {

	this.commedilizieTipologieService = commedilizieTipologieService;
    }

    @Autowired
    public void setCommissioniedilizieRService(CommissioniedilizieRService commissioniedilizieRService) {

	this.commissioniedilizieRService = commissioniedilizieRService;
    }

    @Autowired
    public void setCommedilizieAllegatiService(CommedilizieAllegatiService commedilizieAllegatiService) {

	this.commedilizieAllegatiService = commedilizieAllegatiService;
    }

    @Autowired
    public void setCommedilizieAppelloService(CommedilizieAppelloService commedilizieAppelloService) {

	this.commedilizieAppelloService = commedilizieAppelloService;
    }

    @Autowired
    public void setCommedilizieConvocazioniService(CommedilizieConvocazioniService commedilizieConvocazioniService) {

	this.commedilizieConvocazioniService = commedilizieConvocazioniService;
    }

    @Override
    protected Class<CommissioniedilizieT> getEntityClass() {

	return CommissioniedilizieT.class;
    }

    @Override
    public List<CommissioniedilizieT> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return commissioniedilizietDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(CommissioniedilizieT entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    commissioniedilizietDAO.insert(entity);
	    childDataInsert(entity);
	}
	// §§§END§§§
    }

    @Override
    public CommissioniedilizieT findById(PkId id) {

	return commissioniedilizietDAO.findById(id);
    }

    @Override
    public void update(CommissioniedilizieT entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    commissioniedilizietDAO.update(entity);
	    childDataUpdate(entity);
	}
	// §§§END§§§
    }

    @Override
    protected boolean validateEntity(CommissioniedilizieT entity) {

	if (Boolean.FALSE.equals(entity.getFlagaperta()) && entity.getDataFine() == null) {
	    throw new RuntimeException("Impossibile chiudere una commissione senza indicare la data di fine");
	}
	return super.validateEntity(entity);
    }

    @Override
    public void delete(CommissioniedilizieT entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    commissioniedilizietDAO.delete(entity);
	}
	// §§§END§§§
    }

    @Override
    public void updateCommissioniedilizieTAndChild(CommissioniedilizieTCommand entity) {

	// §§§BEGIN§§§
	this.update(entity.getEntity());
	childDataUpdate(entity);
	// §§§END§§§
    }

    private void childDataInsert(CommissioniedilizieT entity) {

    }

    private void childDataUpdate(CommissioniedilizieT entity) {

    }

    private void childDataUpdate(CommissioniedilizieTCommand entity) {

	// §§§BEGIN§§§
	List<String> listaCodicicommissioniR = null;
	List<String> listaOrdinecommissioniR = null;
	if (StringUtils.isNotBlank(entity.getCodiciCommissioniRScelte())) {
	    listaCodicicommissioniR = Utilities.split(entity.getCodiciCommissioniRScelte(), ",");
	    if (StringUtils.isNotBlank(entity.getCodiciCommissioniRScelte())) {
		listaOrdinecommissioniR = Utilities.split(entity.getOrdine(), ",");
	    }
	    int i = 0;
	    for (String codice : listaCodicicommissioniR) {
		CommissioniedilizieR commissioniedilizieR = commissioniedilizieRService.findById(new PkId(Integer.parseInt(codice)));
		commissioniedilizieR.setOrdine(Integer.parseInt(listaOrdinecommissioniR.get(i)));
		i++;
	    }
	}
	// §§§END§§§
    }

    @Override
    protected void childDelete(CommissioniedilizieT entity) {

	// §§§BEGIN§§§
	Set<CommissioniedilizieR> commissioniedilizieRs = entity.getCommissioniedilizieRs();
	for (CommissioniedilizieR commissioniedilizieR : commissioniedilizieRs) {
	    commissioniedilizieRService.delete(commissioniedilizieR);
	}
	Set<CommedilizieAllegati> allegatis = entity.getCommedilizieAllegatis();
	for (CommedilizieAllegati commedilizieAllegati : allegatis) {
	    commedilizieAllegatiService.delete(commedilizieAllegati);
	}
	Set<CommedilizieAppello> commedilizieAppellos = entity.getCommedilizieAppellos();
	for (CommedilizieAppello commedilizieAppello : commedilizieAppellos) {
	    commedilizieAppelloService.delete(commedilizieAppello);
	}
	Set<CommedilizieConvocazioni> convocazionis = entity.getCommedilizieConvocazionis();
	for (CommedilizieConvocazioni commedilizieConvocazioni : convocazionis) {
	    commedilizieConvocazioniService.deleteWithoutControl(commedilizieConvocazioni);
	}
	// §§§END§§§
    }

    private void dataIntegration(CommissioniedilizieT entity) {

	// §§§BEGIN§§§
	if (entity == null) {
	    throw new IllegalArgumentException("La commissione edilizia t passata è nulla");
	}
	if (entity.getData() == null) {
	    entity.setData(Calendar.getInstance().getTime());
	}
	fixMergeEntityProperties(entity);
	// §§§END§§§
    }

    protected void fixMergeEntityProperties(CommissioniedilizieT entity) {

	// §§§BEGIN§§§
	CommedilizieTipologie commedilizieTipologie = commedilizieTipologieService.bindDomainObject(entity.getCommedilizieTipologie(), PkId.class,
		"id.codice");
	entity.setCommedilizieTipologie(commedilizieTipologie);
	// §§§END§§§
    }

    @Override
    public List<CommissioniedilizieT> findByFilterTable(FilterTable filterTable) {

	// §§§BEGIN§§§
	return commissioniedilizietDAO.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
    //    protected boolean isDeleteAllowed(CommissioniedilizieT entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }

    @Override
    public CommissioniTHelper findByCodiceIstanza(Integer codiceIstanza) {

	return commissioniedilizietDAO.findByCodiceIstanza(codiceIstanza);
    }
}
