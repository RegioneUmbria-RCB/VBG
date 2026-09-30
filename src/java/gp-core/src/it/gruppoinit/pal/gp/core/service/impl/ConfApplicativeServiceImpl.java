package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ConfApplicativeDAO;
import it.gruppoinit.pal.gp.core.domain.ConfApplicative;
import it.gruppoinit.pal.gp.core.domain.ConfApplicativeId;
import it.gruppoinit.pal.gp.core.service.ConfApplicativeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfApplicativeServiceImpl extends BaseServiceImpl<ConfApplicative, ConfApplicativeId> implements ConfApplicativeService {

    private ConfApplicativeDAO confApplicativeDAO;

    @Autowired
    public void setConfApplicativeDAO(ConfApplicativeDAO confApplicativeDAO) {

	this.confApplicativeDAO = confApplicativeDAO;
    }

    @Override
    public void insert(ConfApplicative entity) {

	if (validateEntity(entity)) {
	    confApplicativeDAO.insert(entity);
	}
    }

    @Override
    public void update(ConfApplicative entity) {

	if (validateEntity(entity)) {
	    confApplicativeDAO.update(entity);
	}
    }

    @Override
    public void delete(ConfApplicative entity) {

	confApplicativeDAO.delete(entity);
    }

    @Override
    public List<ConfApplicative> findAll(Integer firstResult, Integer maxResult) {

	return confApplicativeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public ConfApplicative findById(ConfApplicativeId id) {

	return confApplicativeDAO.findById(id);
    }

    @Override
    protected Class<ConfApplicative> getEntityClass() {

	return confApplicativeDAO.getEntityClass();
    }
}
