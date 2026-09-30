package it.gruppoinit.pal.gp.core.features.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskbase;
import it.gruppoinit.pal.gp.core.domain.Taskscheduler;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.scheduler.audit.EscuzionePianificazioniAuditLogger;
import it.gruppoinit.pal.gp.core.features.sorteggi.categorie.SorteggiCategorieService;
import it.gruppoinit.pal.gp.core.features.sorteggi.testata.SorteggitestataService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TaskbaseService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class TaskschedulerServiceImpl extends BaseServiceImpl<Taskscheduler, PkId> implements TaskschedulerService {

    public static final Logger logger = LoggerFactory.getLogger(TaskschedulerServiceImpl.class);
    private TaskschedulerDAO taskschedulerDAO;
    private TaskbaseService taskbaseService;
    private TaskschedulerparametriService taskschedulerparametriService;
    private AlberoprocService alberoProcService;
    private AmministrazioniService amministrazioniService;
    private ComuniassociatiService comuniAssociatiService;
    private MailtipoService mailTipoService;
    private ResponsabiliService responsabiliService;
    private SorteggiCategorieService sorteggiCategorieService;
    private SorteggitestataService sorteggitestataService;
    private TipiMovimentoService tipiMovimentoService;

    @Autowired
    public void setTaskschedulerDAO(TaskschedulerDAO taskschedulerDAO) {

	this.taskschedulerDAO = taskschedulerDAO;
    }

    @Autowired
    public void setTaskbaseService(TaskbaseService taskbaseService) {

	this.taskbaseService = taskbaseService;
    }

    @Autowired
    public void setTaskschedulerparametriService(TaskschedulerparametriService taskschedulerparametriService) {

	this.taskschedulerparametriService = taskschedulerparametriService;
    }

    @Autowired
    public void setAlberoProcService(AlberoprocService alberoProcService) {

	this.alberoProcService = alberoProcService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setComuniAssociatiService(ComuniassociatiService comuniAssociatiService) {

	this.comuniAssociatiService = comuniAssociatiService;
    }

    @Autowired
    public void setMailTipoService(MailtipoService mailTipoService) {

	this.mailTipoService = mailTipoService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setSorteggiCategorieService(SorteggiCategorieService sorteggiCategorieService) {

	this.sorteggiCategorieService = sorteggiCategorieService;
    }

    @Autowired
    public void setSorteggitestataService(SorteggitestataService sorteggitestataService) {

	this.sorteggitestataService = sorteggitestataService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Override
    protected Class<Taskscheduler> getEntityClass() {

	return Taskscheduler.class;
    }

    @Override
    public List<Taskscheduler> findAll(Integer firstResult, Integer maxResult) {

	return taskschedulerDAO.findAll(firstResult, maxResult);
    }

    @Override
    public List<TaskBean> findAll() {

	return this.taskschedulerDAO.findAll();
    }

    @Override
    public void insert(Taskscheduler entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Set<Taskschedulerparametri> taskschedulerparametris = entity.getTaskschedulerparametris();
	    entity.setTaskschedulerparametris(null);
	    taskschedulerDAO.insert(entity);
	    childDataInsert(entity, taskschedulerparametris);
	}
    }

    @Override
    public Taskscheduler findById(PkId id) {

	return taskschedulerDAO.findById(id);
    }

    @Override
    public void update(Taskscheduler entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    taskschedulerDAO.update(entity);
	}
    }

    @Override
    public void delete(Taskscheduler entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    taskschedulerDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Taskscheduler entity) {

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

    private void dataIntegration(Taskscheduler entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro operazione pianificata è nullo");
	}
	if (entity.getAttivo() == null) {
	    entity.setAttivo(Boolean.FALSE);
	}
	if (entity.getInesecuzione() == null) {
	    entity.setInesecuzione(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Taskscheduler entity) {

	Taskbase taskbase = taskbaseService.bindDomainObject(entity.getTaskbase(), String.class, "task");
	entity.setTaskbase(taskbase);
    }

    private void childDataInsert(Taskscheduler entity, Set<Taskschedulerparametri> taskschedulerparametris) {

	for (Taskschedulerparametri taskschedulerparametri : taskschedulerparametris) {
	    taskschedulerparametri.getId().setFkidjob(entity.getId().getCodice());
	    taskschedulerparametriService.insert(taskschedulerparametri);
	}
    }

    protected void childDelete(Taskscheduler entity) {

	Set<Taskschedulerparametri> taskschedulerparametris = entity.getTaskschedulerparametris();
	for (Taskschedulerparametri taskschedulerparametri : taskschedulerparametris) {
	    taskschedulerparametriService.delete(taskschedulerparametri);
	}
    }

    @Override
    public void saveParametri(Taskscheduler entity) {

	// Cancellazione vecchi parametri 
	Taskscheduler taskschedulerOld = this.findById(entity.getId());
	Set<Taskschedulerparametri> taskschedulerparametrisOld = taskschedulerOld.getTaskschedulerparametris();
	if (taskschedulerparametrisOld != null && !taskschedulerparametrisOld.isEmpty()) {
	    for (Taskschedulerparametri taskschedulerparametri : taskschedulerparametrisOld) {
		taskschedulerparametriService.delete(taskschedulerparametri);
	    }
	}
	// Inserimento dei nuovi
	Set<Taskschedulerparametri> taskschedulerparametris = entity.getTaskschedulerparametris();
	for (Taskschedulerparametri taskschedulerparametri : taskschedulerparametris) {
	    taskschedulerparametri.getId().setFkidjob(entity.getId().getCodice());
	    taskschedulerparametri.setTaskscheduler(entity);
	    taskschedulerparametriService.insert(taskschedulerparametri);
	}
    }

    @Override
    public void elabora(boolean aggiornaProssimaEsecuzione) {

	try {
	    if (this.existsSchedulazioneInCorso()) {
		EscuzionePianificazioniAuditLogger.logger.info(
			"Per l'idcomune {} sono presenti schedulazioni in corso, pertanto non verrà avviato un nuovo processamento",
			ORMHelper.getIdcomune());
		return;
	    }
	    List<Taskscheduler> schedulazioni = this.taskschedulerDAO.findDaEseguire();
	    if (schedulazioni.isEmpty()) {
		EscuzionePianificazioniAuditLogger.logger.info("Per l'idcomune {} non sono presenti schedulazioni attive da processare",
			ORMHelper.getIdcomune());
		return;
	    }
	    TaskSchedulerServiceFactory factory = new TaskSchedulerServiceFactory(this.alberoProcService, this.amministrazioniService,
		    this.comuniAssociatiService, this.mailTipoService, this.responsabiliService, this.sorteggiCategorieService,
		    this.sorteggitestataService, this.tipiMovimentoService);
	    for (Taskscheduler taskscheduler : schedulazioni) {
		this.elabora(factory, taskscheduler, aggiornaProssimaEsecuzione);
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void elabora(Taskscheduler task, boolean aggiornaProssimaEsecuzione) {

	try {
	    TaskSchedulerServiceFactory factory = new TaskSchedulerServiceFactory(this.alberoProcService, this.amministrazioniService,
		    this.comuniAssociatiService, this.mailTipoService, this.responsabiliService, this.sorteggiCategorieService,
		    this.sorteggitestataService, this.tipiMovimentoService);
	    this.elabora(factory, task, aggiornaProssimaEsecuzione);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private void elabora(TaskSchedulerServiceFactory factory, Taskscheduler task, boolean aggiornaProssimaEsecuzione) {

	List<Taskschedulerparametri> parametri = this.taskschedulerparametriService.findByTaskId(task.getId().getCodice());
	if (!parametri.isEmpty()) {
	    task.setTaskschedulerparametris(new HashSet<Taskschedulerparametri>(parametri));
	}
	EscuzionePianificazioniAuditLogger.logger.info("Per l'idcomune {} inizio elaborazione schedulazione {}",
		new Object[] { ORMHelper.getIdcomune(), task.toString() });
	this.impostaTaskInEsecuzione(task);
	this.taskschedulerDAO.commitFlush();
	ITaskSchedulerService service = factory.get(TaskEnum.valueOf(task.getTaskbase().getTask()));
	EscuzionePianificazioniAuditLogger.logger.debug("Elaboro il task");
	service.elabora(task);
	if (aggiornaProssimaEsecuzione) {
	    this.aggiornaProssimaElaborazione(task);
	}
	this.taskschedulerDAO.commitFlush();
	EscuzionePianificazioniAuditLogger.logger.info("Per l'idcomune {} fine elaborazione schedulazione {}",
		new Object[] { ORMHelper.getIdcomune(), task.toString() });
    }

    private boolean existsSchedulazioneInCorso() {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("inesecuzione", true, Boolean.class));
	filterTable.addRestriction(filterRestriction);
	return this.taskschedulerDAO.countRecord(filterTable) > 0;
    }

    private void impostaTaskInEsecuzione(Taskscheduler taskscheduler) {

	EscuzionePianificazioniAuditLogger.logger.debug("Imposto il task in esecuzione");
	taskscheduler.setInesecuzione(true);
	this.taskschedulerDAO.update(taskscheduler);
    }

    private void aggiornaProssimaElaborazione(Taskscheduler taskscheduler) {

	EscuzionePianificazioniAuditLogger.logger.debug("Imposto il task come eseguito e valorizzo la prossima esecuzione");
	Integer minuti = taskscheduler.getIntervallo();
	Date prossimaEsecuzione = taskscheduler.getProssimaesecuzione();
	if (minuti != null && prossimaEsecuzione != null) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(prossimaEsecuzione);
	    cal.add(Calendar.MINUTE, minuti);
	    taskscheduler.setProssimaesecuzione(cal.getTime());
	}
	taskscheduler.setInesecuzione(false);
	this.taskschedulerDAO.update(taskscheduler);
    }
}