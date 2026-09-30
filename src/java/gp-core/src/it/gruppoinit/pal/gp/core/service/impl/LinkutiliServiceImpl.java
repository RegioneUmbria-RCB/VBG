package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.LinkutiliDAO;
import it.gruppoinit.pal.gp.core.domain.Linkutili;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.LinkutiliService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 */
@Service
public class LinkutiliServiceImpl extends BaseServiceImpl<Linkutili, PkId> implements LinkutiliService {

    private LinkutiliDAO linkutiliDAO;

    @Autowired
    public void setLinkutiliDAO(LinkutiliDAO linkutiliDAO) {

	this.linkutiliDAO = linkutiliDAO;
    }

    @Override
    protected Class<Linkutili> getEntityClass() {

	return Linkutili.class;
    }

    @Override
    public List<Linkutili> findAll(Integer firstResult, Integer maxResult) {

	return linkutiliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Linkutili entity) {

	if (validateEntity(entity)) {
	    linkutiliDAO.insert(entity);
	}
    }

    @Override
    public Linkutili findById(PkId id) {

	return linkutiliDAO.findById(id);
    }

    @Override
    public void update(Linkutili entity) {

	if (validateEntity(entity)) {
	    linkutiliDAO.update(entity);
	}
    }

    @Override
    public void delete(Linkutili entity) {

	if (isDeleteAllowed(entity)) {
	    linkutiliDAO.delete(entity);
	}
    }
}
