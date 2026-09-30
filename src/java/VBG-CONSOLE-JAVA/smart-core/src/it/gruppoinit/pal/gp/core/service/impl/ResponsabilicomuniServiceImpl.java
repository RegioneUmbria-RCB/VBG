/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabilicomuniDAO;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.ResponsabilicomuniId;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * @author Luca Proietti
 * 
 */
@Service
public class ResponsabilicomuniServiceImpl extends BaseServiceImpl<Responsabilicomuni, ResponsabilicomuniId> implements ResponsabilicomuniService {

    private ResponsabilicomuniDAO responsabilicomuniDAO;

    @Autowired
    public void setResponsabilicomuniDAO(ResponsabilicomuniDAO responsabilicomuniDAO) {

	this.responsabilicomuniDAO = responsabilicomuniDAO;
    }

    @Override
    protected Class<Responsabilicomuni> getEntityClass() {

	return Responsabilicomuni.class;
    }

    @Override
    public void delete(Responsabilicomuni entity) {

	responsabilicomuniDAO.delete(entity);
    }

    @Override
    public List<Responsabilicomuni> findAll(Integer firstResult, Integer maxResult) {

	return responsabilicomuniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Responsabilicomuni findById(ResponsabilicomuniId id) {

	return responsabilicomuniDAO.findById(id);
    }

    @Override
    public void insert(Responsabilicomuni entity) {

	if (validateEntity(entity)) {
	    responsabilicomuniDAO.insert(entity);
	}
    }

    @Override
    public void update(Responsabilicomuni entity) {

	if (validateEntity(entity)) {
	    responsabilicomuniDAO.update(entity);
	}
    }

    @Override
    public List<Responsabilicomuni> findByOperatore(Responsabili responsabile) {

	return responsabilicomuniDAO.findByOperatore(responsabile);
    }

    @Override
    public void deleteByResponsabile(Responsabili entity) {

	List<Responsabilicomuni> list = this.findByOperatore(entity);
	for (Responsabilicomuni responsabilicomuni : list) {
	    this.delete(responsabilicomuni);
	}
    }
}
