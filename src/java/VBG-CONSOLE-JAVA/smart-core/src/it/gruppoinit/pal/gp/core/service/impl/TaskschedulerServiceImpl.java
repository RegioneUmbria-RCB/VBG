package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TaskschedulerDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskbase;
import it.gruppoinit.pal.gp.core.domain.Taskscheduler;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.service.TaskbaseService;
import it.gruppoinit.pal.gp.core.service.TaskschedulerService;
import it.gruppoinit.pal.gp.core.service.TaskschedulerparametriService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class TaskschedulerServiceImpl extends BaseServiceImpl<Taskscheduler, PkId> implements TaskschedulerService {

    private TaskschedulerDAO taskschedulerDAO;
    private TaskbaseService taskbaseService;
    private TaskschedulerparametriService taskschedulerparametriService;

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

    @Override
    protected Class<Taskscheduler> getEntityClass() {

	return Taskscheduler.class;
    }

    @Override
    public List<Taskscheduler> findAll(Integer firstResult, Integer maxResult) {

	return taskschedulerDAO.findAll(firstResult, maxResult);
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
	    taskschedulerparametriService.insert(taskschedulerparametri);
	}
    }
}
