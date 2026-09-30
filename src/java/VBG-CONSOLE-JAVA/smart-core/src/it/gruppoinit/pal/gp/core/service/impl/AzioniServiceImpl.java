package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AzioniDAO;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.service.AzioniService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AzioniServiceImpl extends BaseServiceImpl<Azioni, Integer> implements AzioniService {

    private AzioniDAO azioniDAO;

    @Autowired
    public void setAzioniDAO(AzioniDAO azioniDAO) {

	this.azioniDAO = azioniDAO;
    }

    @Override
    protected Class<Azioni> getEntityClass() {

	return Azioni.class;
    }

    @Override
    public void delete(Azioni entity) {

	azioniDAO.delete(entity);
    }

    @Override
    public List<Azioni> findAll(Integer firstResult, Integer maxResult) {

	return azioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Azioni findById(Integer id) {

	return azioniDAO.findById(id);
    }

    @Override
    public void insert(Azioni entity) {

	if (validateEntity(entity)) {
	    azioniDAO.insert(entity);
	}
    }

    @Override
    public void update(Azioni entity) {

	if (validateEntity(entity)) {
	    azioniDAO.update(entity);
	}
    }
}
