package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.InterventiDAO;
import it.gruppoinit.pal.gp.core.domain.Interventi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.InterventiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 */
@Service
public class InterventiServiceImpl extends BaseServiceImpl<Interventi, PkId> implements InterventiService {

    private InterventiDAO interventiDAO;

    @Autowired
    public void setInterventiDAO(InterventiDAO interventiDAO) {

	this.interventiDAO = interventiDAO;
    }

    @Override
    protected Class<Interventi> getEntityClass() {

	return Interventi.class;
    }

    @Override
    public List<Interventi> findAll(Integer firstResult, Integer maxResult) {

	return interventiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Interventi entity) {

	if (validateEntity(entity)) {
	    interventiDAO.insert(entity);
	}
    }

    @Override
    public Interventi findById(PkId id) {

	return interventiDAO.findById(id);
    }

    @Override
    public void update(Interventi entity) {

	if (validateEntity(entity)) {
	    interventiDAO.update(entity);
	}
    }

    @Override
    public void delete(Interventi entity) {

	if (isDeleteAllowed(entity)) {
	    interventiDAO.delete(entity);
	}
    }
}
