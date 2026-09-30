package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.audit.Loggable;
import it.gruppoinit.pal.gp.core.domain.BollGestIstanzeoneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollGestIstanzeoneriDAO;
import it.gruppoinit.pal.gp.core.service.BollGestIstanzeoneriService;

/**
 * 
 * @author
 */
@Loggable(featureName = "bollettazione")
@Service
public class BollGestIstanzeoneriServiceImpl extends BaseServiceImpl<BollGestIstanzeoneri, PkId> implements BollGestIstanzeoneriService {

    private BollGestIstanzeoneriDAO bollgestistanzeoneriDAO;

    @Autowired
    public void setBollGestIstanzeoneriDAO(BollGestIstanzeoneriDAO bollgestistanzeoneriDAO) {

	this.bollgestistanzeoneriDAO = bollgestistanzeoneriDAO;
    }

    @Override
    protected Class<BollGestIstanzeoneri> getEntityClass() {

	return BollGestIstanzeoneri.class;
    }

    @Override
    public List<BollGestIstanzeoneri> findAll(Integer firstResult, Integer maxResult) {

	return bollgestistanzeoneriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(BollGestIstanzeoneri entity) {

	if (validateEntity(entity)) {
	    bollgestistanzeoneriDAO.insert(entity);
	}
    }

    @Override
    public BollGestIstanzeoneri findById(PkId id) {

	return bollgestistanzeoneriDAO.findById(id);
    }

    @Override
    public void update(BollGestIstanzeoneri entity) {

	if (validateEntity(entity)) {
	    bollgestistanzeoneriDAO.update(entity);
	}
    }

    @Override
    public void delete(BollGestIstanzeoneri entity) {

	if (isDeleteAllowed(entity)) {
	    bollgestistanzeoneriDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(BollGestIstanzeoneri entity) {

	return true;
    }
}
