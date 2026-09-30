package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.CommedilizieAppelloDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppelloPratiche;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.commissioni.appello.ICommissioniAppelloDAO;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.ICommissioniAuditingService;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioSoggettoAppelloAggiornato;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioSoggettoAppelloEliminato;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioSoggettoAppelloNuovo;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.CommedilizieAppelloService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieTService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

/**
 * 
 * @author gianpaolot
 */
@Service
public class CommedilizieAppelloServiceImpl extends BaseServiceImpl<CommedilizieAppello, PkId> implements CommedilizieAppelloService {

    private CommedilizieAppelloDAO commedilizieappelloDAO;
    private ResponsabiliService responsabiliService;
    private AmministrazioniService amministrazioniService;
    private CommissioniedilizieTService commissioniedilizieTService;
    private ICommissioniAppelloDAO commissioniAppelloDAO;
    private AnagrafeService anagrafeService;
    private ICommissioniAuditingService auditingService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setAuditingService(ICommissioniAuditingService auditingService) {

	this.auditingService = auditingService;
    }

    @Autowired
    public void setCommissioniAppelloDAO(ICommissioniAppelloDAO commissioniAppelloDAO) {

	this.commissioniAppelloDAO = commissioniAppelloDAO;
    }

    @Autowired
    public void setCommedilizieAppelloDAO(CommedilizieAppelloDAO commedilizieappelloDAO) {

	this.commedilizieappelloDAO = commedilizieappelloDAO;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setCommissioniedilizieTService(CommissioniedilizieTService commissioniedilizieTService) {

	this.commissioniedilizieTService = commissioniedilizieTService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Override
    protected Class<CommedilizieAppello> getEntityClass() {

	return CommedilizieAppello.class;
    }

    @Override
    public List<CommedilizieAppello> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return commedilizieappelloDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(CommedilizieAppello entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    commedilizieappelloDAO.insert(entity);
	    childDataInsert(entity);
	}
	// §§§END§§§
    }

    @Override
    public CommedilizieAppello findById(PkId id) {

	// §§§BEGIN§§§
	return commedilizieappelloDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(CommedilizieAppello entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity) && isUpdateAllowed(entity)) {
	    commedilizieappelloDAO.update(entity);
	    childDataUpdate(entity);
	    this.auditingService.log(entity.getCommissioniedilizieT().getId().getCodice(),
		    new MessaggioSoggettoAppelloAggiornato(this.getResponsabile(), entity.getComponente()));
	}
	// §§§END§§§
    }

    @Override
    public void delete(CommedilizieAppello entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    this.commedilizieappelloDAO.delete(entity);
	    this.auditingService.log(entity.getCommissioniedilizieT().getId().getCodice(),
		    new MessaggioSoggettoAppelloEliminato(this.getResponsabile(), entity.getComponente()));
	}
	// §§§END§§§
    }

    @Override
    public List<CommedilizieAppello> findByFilterTable(FilterTable filterTable) {

	// §§§BEGIN§§§
	return commedilizieappelloDAO.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<CommedilizieAppello> findByCommissioniT(CommissioniedilizieT commissioniedilizieT) {

	// §§§BEGIN§§§
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("commissioniedilizieT", commissioniedilizieT, CommissioniedilizieT.class));
	filterTable.addRestriction(filterRestriction);
	filterTable.addOrder(FilterUtils.orderDesc("ordinamento", "commedilizieCarica"));
	List<CommedilizieAppello> list = this.findByFilterTable(filterTable);
	return list;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    protected boolean isDeleteAllowed(CommedilizieAppello entity) {

	boolean delete = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getCommedilizieVotazionis().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "COMMEDILIZIE_VOTAZIONI", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return delete;
    }

    private boolean isInsertAllowed(CommedilizieAppello entity) {

	boolean isInsert = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// responsabile e ammistratore non sono obbligatori sul DB, in quanto può essere inserito o l'uno o l'altro a seconda di 
	//una scelta preliminare.
	// Logicamente : E' necessario che uno dei due sia sempre prente al salvataggio dell'oggetto
	// Se entrambi vengono passati nullo rilancio l'errore.
	if (entity.getResponsabile() == null && entity.getAmministrazioni() == null && entity.getAnagrafe() == null) {
	    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", CommedilizieAppello.class, "responsabile", entity.getResponsabile(),
		    entity));
	    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", CommedilizieAppello.class, "amministrazioni", entity.getAmministrazioni(),
		    entity));
	    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", CommedilizieAppello.class, "anagrafe", entity.getAnagrafe(), entity));
	}
	// Giuseppe: commentato per modifica Commissioni edilizie
	//	if (entity.getAmministrazioni() != null && StringUtils.isBlank(entity.getReferente())) {
	//	    _ivs.add(new InvalidValue("service_error.non_puo_essere_referente_se_amministrazione", null, null, null, entity));
	//	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return isInsert;
    }

    private boolean isUpdateAllowed(CommedilizieAppello entity) {

	// ha lo stesso comportamento del controllo in inserimento
	return isInsertAllowed(entity);
    }

    private void childDataInsert(CommedilizieAppello entity) {

    }

    private void childDataUpdate(CommedilizieAppello entity) {

    }

    @SuppressWarnings("unchecked")
    @Override
    protected void childDelete(CommedilizieAppello entity) {

	Set<CommedilizieAppelloPratiche> appelloPratiche = entity.getCommedilizieappellopratiches();
	for (CommedilizieAppelloPratiche pratica : appelloPratiche) {
	    this.commissioniAppelloDAO.delete(pratica);
	}
    }

    private void dataIntegration(CommedilizieAppello entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'allegato della commissione passato è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(CommedilizieAppello entity) {

	// §§§BEGIN§§§
	CommissioniedilizieT commissioniedilizieT = commissioniedilizieTService.bindDomainObject(entity.getCommissioniedilizieT(), PkId.class,
		"id.codice");
	entity.setCommissioniedilizieT(commissioniedilizieT);
	Responsabili responsabili = responsabiliService.bindDomainObject(entity.getResponsabile(), PkId.class, "id.codice");
	entity.setResponsabile(responsabili);
	Amministrazioni amministrazioni = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	entity.setAmministrazioni(amministrazioni);
	Anagrafe anagrafe = anagrafeService.bindDomainObject(entity.getAnagrafe(), PkId.class, "id.codice");
	entity.setAnagrafe(anagrafe);
	// §§§END§§§
    }

    @Override
    public List<CommedilizieAppello> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "amministrazioni", Integer.class));
	filterTable.addRestriction(fr);
	return commedilizieappelloDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(CommedilizieAppello entity, List<Integer> codiciCommissioniEdilizieR) {

	this.insert(entity);
	if (codiciCommissioniEdilizieR != null) {
	    for (Integer codiceCommissioneEdR : codiciCommissioniEdilizieR) {
		CommedilizieAppelloPratiche e = new CommedilizieAppelloPratiche();
		e.setCommedilizieAppello(entity);
		e.setCommissioniedilizieR((CommissioniedilizieR) commissioniAppelloDAO.getById(CommissioniedilizieR.class, codiceCommissioneEdR));
		commissioniAppelloDAO.saveEntity(e);
	    }
	}
	this.auditingService.log(entity.getCommissioniedilizieT().getId().getCodice(),
		new MessaggioSoggettoAppelloNuovo(this.getResponsabile(), entity.getComponente()));
    }

    private String getResponsabile() {

	return ((Responsabili) this.userSecurityService.getCurrentlyAuthenticatedUserDetails()).getResponsabile();
    }

    @Override
    public List<CommedilizieAppelloPratiche> findCommEdilizieAppelloPraticheByAppello(Integer idAppello) {

	return commissioniAppelloDAO.findCommEdilizieAppelloPraticheByAppello(idAppello);
    }

    @Override
    public void update(CommedilizieAppello entity, List<Integer> codiciCommissioniEdilizieR) {

	this.update(entity);
	commissioniAppelloDAO.deleteAppelloPraticheByAppello(entity.getId().getCodice());
	if (codiciCommissioniEdilizieR != null) {
	    for (Integer codiceCommissioneEdR : codiciCommissioniEdilizieR) {
		CommedilizieAppelloPratiche e = new CommedilizieAppelloPratiche();
		e.setCommedilizieAppello(entity);
		e.setCommissioniedilizieR((CommissioniedilizieR) commissioniAppelloDAO.getById(CommissioniedilizieR.class, codiceCommissioneEdR));
		commissioniAppelloDAO.saveEntity(e);
	    }
	}
	this.auditingService.log(entity.getCommissioniedilizieT().getId().getCodice(),
		new MessaggioSoggettoAppelloAggiornato(this.getResponsabile(), entity.getComponente()));
    }

    @Override
    public List<CommedilizieAppello> findByCommissioniEdilizieR(Integer idCommissioniedilizieR) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("commissioniedilizieRId", idCommissioniedilizieR, "commedilizieappellopratiches", PkId.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	return commedilizieappelloDAO.findByFilterTable(ft);
    }
}
