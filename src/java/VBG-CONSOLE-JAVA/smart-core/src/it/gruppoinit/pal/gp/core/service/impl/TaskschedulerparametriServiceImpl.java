package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TaskschedulerparametriDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskscheduler;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.service.TaskschedulerService;
import it.gruppoinit.pal.gp.core.service.TaskschedulerparametriService;

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
public class TaskschedulerparametriServiceImpl extends BaseServiceImpl<Taskschedulerparametri, PkId> implements TaskschedulerparametriService {

    private TaskschedulerparametriDAO taskschedulerparametriDAO;
    private TaskschedulerService taskschedulerService;

    @Autowired
    public void setTaskschedulerparametriDAO(TaskschedulerparametriDAO taskschedulerparametriDAO) {

	this.taskschedulerparametriDAO = taskschedulerparametriDAO;
    }

    @Autowired
    public void setTaskschedulerService(TaskschedulerService taskschedulerService) {

	this.taskschedulerService = taskschedulerService;
    }

    @Override
    protected Class<Taskschedulerparametri> getEntityClass() {

	return Taskschedulerparametri.class;
    }

    @Override
    public List<Taskschedulerparametri> findAll(Integer firstResult, Integer maxResult) {

	return taskschedulerparametriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Taskschedulerparametri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    taskschedulerparametriDAO.insert(entity);
	}
    }

    @Override
    public Taskschedulerparametri findById(PkId id) {

	return taskschedulerparametriDAO.findById(id);
    }

    @Override
    public void update(Taskschedulerparametri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    taskschedulerparametriDAO.update(entity);
	}
    }

    @Override
    public void delete(Taskschedulerparametri entity) {

	if (isDeleteAllowed(entity)) {
	    taskschedulerparametriDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Taskschedulerparametri entity) {

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

    private void dataIntegration(Taskschedulerparametri entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro dell'operazione pianificata è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Taskschedulerparametri entity) {

	Taskscheduler taskscheduler = taskschedulerService.bindDomainObject(entity.getTaskscheduler(), PkId.class, "id.codice");
	entity.setTaskscheduler(taskscheduler);
    }
}
