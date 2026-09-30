package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.CommedilizieConvocazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CommedilizieConvocazioni;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.ICommissioniAuditingService;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioConvocazioneAggiornata;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioConvocazioneCancellata;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioNuovaConvocazione;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CommedilizieConvocazioniService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieTService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

/**
 * 
 * @author gianpaolot
 */
@Service
public class CommedilizieConvocazioniServiceImpl extends BaseServiceImpl<CommedilizieConvocazioni, PkId> implements CommedilizieConvocazioniService {

    private CommedilizieConvocazioniDAO commedilizieconvocazioniDAO;
    private CommissioniedilizieTService commissioniedilizieTService;
    private ICommissioniAuditingService auditingService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setCommedilizieconvocazioniDAO(CommedilizieConvocazioniDAO commedilizieconvocazioniDAO) {

	this.commedilizieconvocazioniDAO = commedilizieconvocazioniDAO;
    }

    @Autowired
    public void setCommissioniedilizieTService(CommissioniedilizieTService commissioniedilizieTService) {

	this.commissioniedilizieTService = commissioniedilizieTService;
    }

    @Autowired
    public void setAuditingService(ICommissioniAuditingService auditingService) {

	this.auditingService = auditingService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Override
    protected Class<CommedilizieConvocazioni> getEntityClass() {

	return CommedilizieConvocazioni.class;
    }

    @Override
    public List<CommedilizieConvocazioni> findAll(Integer firstResult, Integer maxResult) {

	return commedilizieconvocazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CommedilizieConvocazioni entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    commedilizieconvocazioniDAO.insert(entity);
	    childDataInsert(entity);
	    this.auditingService.log(entity.getCommissioniedilizieT().getId().getCodice(), new MessaggioNuovaConvocazione(this.getResponsabile(),
		    entity.getCommissioniedilizieT().getNumprotocollo(), entity.getDataconvocazione(), entity.getOraconvocazione()));
	}
	// §§§END§§§
    }

    private String getResponsabile() {

	return ((Responsabili) this.userSecurityService.getCurrentlyAuthenticatedUserDetails()).getResponsabile();
    }

    @Override
    public CommedilizieConvocazioni findById(PkId id) {

	// §§§BEGIN§§§
	return commedilizieconvocazioniDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(CommedilizieConvocazioni entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    commedilizieconvocazioniDAO.update(entity);
	    childDataUpdate(entity);
	    this.auditingService.log(entity.getCommissioniedilizieT().getId().getCodice(),
		    new MessaggioConvocazioneAggiornata(this.getResponsabile(), entity.getId().getCodice()));
	}
	// §§§END§§§
    }

    @Override
    public void delete(CommedilizieConvocazioni entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    commedilizieconvocazioniDAO.delete(entity);
	    this.auditingService.log(entity.getCommissioniedilizieT().getId().getCodice(),
		    new MessaggioConvocazioneCancellata(getResponsabile(), entity.getDataconvocazione(), entity.getOraconvocazione()));
	}
	// §§§END§§§
    }

    @Override
    public void deleteWithoutControl(CommedilizieConvocazioni entity) {

	// §§§BEGIN§§§
	childDelete(entity);
	commedilizieconvocazioniDAO.delete(entity);
	// §§§END§§§
    }

    @Override
    public List<CommedilizieConvocazioni> findByFilterTable(FilterTable filterTable) {

	// §§§BEGIN§§§
	return commedilizieconvocazioniDAO.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<CommedilizieConvocazioni> findByCommissioneEdiliziaT(CommissioniedilizieT commissioniedilizieT) {

	// §§§BEGIN§§§
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("commissioniedilizieT", commissioniedilizieT, CommissioniedilizieT.class));
	filterTable.addRestriction(filterRestriction);
	filterTable.addOrder(FilterUtils.orderAsc("dataconvocazione"));
	return this.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    protected boolean isDeleteAllowed(CommedilizieConvocazioni entity) {

	boolean delete = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (EntityUtils.getNestedProperty(entity.getCommissioniedilizieT(), "id.codice") != null) {
	    CommissioniedilizieT commissioniedilizieT = commissioniedilizieTService
		    .findById(new PkId(entity.getCommissioniedilizieT().getId().getCodice()));
	    if (commissioniedilizieT.getIdconvocazione() != null && commissioniedilizieT.getIdconvocazione().equals(entity.getId().getCodice())) {
		_ivs.add(new InvalidValue("service_error.convocazione_associata_alla_commissione", null, null, null, null));
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return delete;
    }

    private void childDataInsert(CommedilizieConvocazioni entity) {

	// §§§BEGIN§§§
	// Controlla che la commissione non abbia una convocazione settata, campo idConvocazione null
	// ed eventualmente gli setta la prima che stiamo creando.
	if (entity.getCommissioniedilizieT() != null) {
	    CommissioniedilizieT commissioniedilizieT = commissioniedilizieTService
		    .findById(new PkId(entity.getCommissioniedilizieT().getId().getCodice()));
	    if (entity.getCommissioniedilizieT().getIdconvocazione() == null) {
		commissioniedilizieT.setIdconvocazione(entity.getId().getCodice());
	    }
	    // se la data non è stata inserita e la convocazione è quella settata nella commissione allora cambio l'ora della commissione altrimenti lascio quella esistente
	    if (StringUtils.isBlank(entity.getCommissioniedilizieT().getOrainizio()) && (entity.getCommissioniedilizieT().getIdconvocazione() != null
		    && entity.getId().getCodice().equals(entity.getCommissioniedilizieT().getIdconvocazione()))) {
		commissioniedilizieT.setOrainizio(entity.getOraconvocazione());
	    }
	    commissioniedilizieTService.update(commissioniedilizieT);
	}
	// §§§END§§§
    }

    private void childDataUpdate(CommedilizieConvocazioni entity) {

	// §§§BEGIN§§§
	// Controlla che la commissione non abbia una convocazione settata, campo idConvocazione null
	// ed eventualmente gli setta la prima che stiamo creando.
	if (entity.getCommissioniedilizieT() != null) {
	    CommissioniedilizieT commissioniedilizieT = commissioniedilizieTService
		    .findById(new PkId(entity.getCommissioniedilizieT().getId().getCodice()));
	    if (commissioniedilizieT.getIdconvocazione() == null) {
		commissioniedilizieT.setIdconvocazione(entity.getId().getCodice());
	    }
	    if (commissioniedilizieT.getData() == null) {
		commissioniedilizieT.setData(entity.getDataconvocazione());
	    } else {
		// se modifico la data di convocazione settata come attiva nella commissione
		// devo anche modificare la data della commissione
		if (entity.getCommissioniedilizieT().getIdconvocazione() != null
			&& entity.getId().getCodice().equals(entity.getCommissioniedilizieT().getIdconvocazione())) {
		    commissioniedilizieT.setData(entity.getDataconvocazione());
		}
	    }
	    commissioniedilizieTService.update(commissioniedilizieT);
	}
	// §§§END§§§
    }

    @Override
    protected void childDelete(CommedilizieConvocazioni entity) {

	super.childDelete(entity);
    }

    private void dataIntegration(CommedilizieConvocazioni entity) {

	// §§§BEGIN§§§
	if (entity == null) {
	    throw new IllegalArgumentException("La convocazione passata è nulla");
	}
	// controllo se per quella commissione esistono già altre convocazioni
	// se è la prima che inserisco allora di default la assosocio e la data della commissione
	// sarà quella della convocazione appena inserita. 
	CommissioniedilizieT commissioniedilizieT = commissioniedilizieTService
		.findById(new PkId(entity.getCommissioniedilizieT().getId().getCodice()));
	List<CommedilizieConvocazioni> listCommissioniPresenti = this.findByCommissioneEdiliziaT(commissioniedilizieT);
	if (listCommissioniPresenti.isEmpty()) {
	    commissioniedilizieT.setData(entity.getDataconvocazione());
	    entity.setCommissioniedilizieT(commissioniedilizieT);
	}
	fixMergeEntityProperties(entity);
	// §§§END§§§
    }

    protected void fixMergeEntityProperties(CommedilizieConvocazioni entity) {

	// §§§BEGIN§§§
	CommissioniedilizieT commissioniedilizieT = commissioniedilizieTService.bindDomainObject(entity.getCommissioniedilizieT(), PkId.class,
		"id.codice");
	entity.setCommissioniedilizieT(commissioniedilizieT);
	// §§§END§§§
    }
}
