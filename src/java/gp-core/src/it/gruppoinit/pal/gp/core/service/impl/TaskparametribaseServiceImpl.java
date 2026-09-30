package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TaskparametribaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskparametribase;
import it.gruppoinit.pal.gp.core.service.TaskparametribaseService;

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
public class TaskparametribaseServiceImpl extends BaseServiceImpl<Taskparametribase, PkId> implements TaskparametribaseService {

    private TaskparametribaseDAO taskparametribaseDAO;

    @Autowired
    public void setTaskparametribaseDAO(TaskparametribaseDAO taskparametribaseDAO) {

	this.taskparametribaseDAO = taskparametribaseDAO;
    }

    @Override
    protected Class<Taskparametribase> getEntityClass() {

	return Taskparametribase.class;
    }

    @Override
    public List<Taskparametribase> findAll(Integer firstResult, Integer maxResult) {

	return taskparametribaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Taskparametribase entity) {

	if (validateEntity(entity)) {
	    taskparametribaseDAO.insert(entity);
	}
    }

    @Override
    public Taskparametribase findById(PkId id) {

	return taskparametribaseDAO.findById(id);
    }

    @Override
    public void update(Taskparametribase entity) {

	if (validateEntity(entity)) {
	    taskparametribaseDAO.update(entity);
	}
    }

    @Override
    public void delete(Taskparametribase entity) {

	if (isDeleteAllowed(entity)) {
	    taskparametribaseDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Taskparametribase entity) {

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
