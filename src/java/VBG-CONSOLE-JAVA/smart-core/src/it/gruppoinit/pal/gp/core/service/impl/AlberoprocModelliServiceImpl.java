package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocModelliDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocModelli;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AlberoprocModelliService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 */
@Service
public class AlberoprocModelliServiceImpl extends BaseServiceImpl<AlberoprocModelli, PkId> implements AlberoprocModelliService {

    private AlberoprocModelliDAO alberoprocmodelliDAO;

    @Autowired
    public void setAlberoprocModelliDAO(AlberoprocModelliDAO alberoprocmodelliDAO) {

	this.alberoprocmodelliDAO = alberoprocmodelliDAO;
    }

    @Override
    protected Class<AlberoprocModelli> getEntityClass() {

	return AlberoprocModelli.class;
    }

    @Override
    public List<AlberoprocModelli> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocmodelliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AlberoprocModelli entity) {

	if (validateEntity(entity)) {
	    alberoprocmodelliDAO.insert(entity);
	}
    }

    @Override
    public AlberoprocModelli findById(PkId id) {

	return alberoprocmodelliDAO.findById(id);
    }

    @Override
    public void update(AlberoprocModelli entity) {

	if (validateEntity(entity)) {
	    alberoprocmodelliDAO.update(entity);
	}
    }

    @Override
    public void delete(AlberoprocModelli entity) {

	if (isDeleteAllowed(entity)) {
	    alberoprocmodelliDAO.delete(entity);
	}
    }
}
