package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocLimitiDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocLimiti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AlberoprocLimitiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 */
@Service
public class AlberoprocLimitiServiceImpl extends BaseServiceImpl<AlberoprocLimiti, PkId> implements AlberoprocLimitiService {

    private AlberoprocLimitiDAO alberoproclimitiDAO;
    private AlberoprocService alberoprocService;

    @Autowired
    public void setAlberoprocLimitiDAO(AlberoprocLimitiDAO alberoproclimitiDAO) {

	this.alberoproclimitiDAO = alberoproclimitiDAO;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Override
    protected Class<AlberoprocLimiti> getEntityClass() {

	return AlberoprocLimiti.class;
    }

    @Override
    public List<AlberoprocLimiti> findAll(Integer firstResult, Integer maxResult) {

	return alberoproclimitiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AlberoprocLimiti entity) {

	if (validateEntity(entity)) {
	    alberoproclimitiDAO.insert(entity);
	}
    }

    @Override
    public AlberoprocLimiti findById(PkId id) {

	return alberoproclimitiDAO.findById(id);
    }

    @Override
    public void update(AlberoprocLimiti entity) {

	if (validateEntity(entity)) {
	    alberoproclimitiDAO.update(entity);
	}
    }

    @Override
    public void delete(AlberoprocLimiti entity) {

	if (isDeleteAllowed(entity)) {
	    alberoproclimitiDAO.delete(entity);
	}
    }
}
