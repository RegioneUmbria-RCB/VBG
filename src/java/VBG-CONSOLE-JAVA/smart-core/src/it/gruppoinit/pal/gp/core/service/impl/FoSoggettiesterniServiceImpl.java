package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoSoggettiesterniDAO;
import it.gruppoinit.pal.gp.core.domain.FoSoggettiesterni;
import it.gruppoinit.pal.gp.core.service.FoSoggettiesterniService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class FoSoggettiesterniServiceImpl extends BaseServiceImpl<FoSoggettiesterni, Integer> implements FoSoggettiesterniService {

    private FoSoggettiesterniDAO fosoggettiesterniDAO;

    @Autowired
    public void setFoSoggettiesterniDAO(FoSoggettiesterniDAO fosoggettiesterniDAO) {

	this.fosoggettiesterniDAO = fosoggettiesterniDAO;
    }

    @Override
    protected Class<FoSoggettiesterni> getEntityClass() {

	return FoSoggettiesterni.class;
    }

    @Override
    public List<FoSoggettiesterni> findAll(Integer firstResult, Integer maxResult) {

	return fosoggettiesterniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoSoggettiesterni entity) {

	if (validateEntity(entity)) {
	    fosoggettiesterniDAO.insert(entity);
	}
    }

    @Override
    public FoSoggettiesterni findById(Integer id) {

	return fosoggettiesterniDAO.findById(id);
    }

    @Override
    public void update(FoSoggettiesterni entity) {

	if (validateEntity(entity)) {
	    fosoggettiesterniDAO.update(entity);
	}
    }

    @Override
    public void delete(FoSoggettiesterni entity) {

	if (isDeleteAllowed(entity)) {
	    fosoggettiesterniDAO.delete(entity);
	}
    }
}
