package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TaskbaseDAO;
import it.gruppoinit.pal.gp.core.domain.Taskbase;
import it.gruppoinit.pal.gp.core.service.TaskbaseService;

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
public class TaskbaseServiceImpl extends BaseServiceImpl<Taskbase, String> implements TaskbaseService {

    private TaskbaseDAO taskbaseDAO;

    @Autowired
    public void setTaskbaseDAO(TaskbaseDAO taskbaseDAO) {

	this.taskbaseDAO = taskbaseDAO;
    }

    @Override
    protected Class<Taskbase> getEntityClass() {

	return Taskbase.class;
    }

    @Override
    public List<Taskbase> findAll(Integer firstResult, Integer maxResult) {

	return taskbaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Taskbase entity) {

	if (validateEntity(entity)) {
	    taskbaseDAO.insert(entity);
	}
    }

    @Override
    public Taskbase findById(String id) {

	return taskbaseDAO.findById(id);
    }

    @Override
    public void update(Taskbase entity) {

	if (validateEntity(entity)) {
	    taskbaseDAO.update(entity);
	}
    }

    @Override
    public void delete(Taskbase entity) {

	if (isDeleteAllowed(entity)) {
	    taskbaseDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Taskbase entity) {

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
}
