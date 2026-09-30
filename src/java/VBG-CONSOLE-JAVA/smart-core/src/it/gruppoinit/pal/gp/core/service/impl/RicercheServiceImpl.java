package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.RicercheDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Ricerche;
import it.gruppoinit.pal.gp.core.service.RicercheService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RicercheServiceImpl extends BaseServiceImpl<Ricerche, PkId> implements RicercheService {

    private RicercheDAO ricercheDAO;

    @Autowired
    public void setRicercheDAO(RicercheDAO ricercheDAO) {

	this.ricercheDAO = ricercheDAO;
    }

    @Override
    protected Class<Ricerche> getEntityClass() {

	return Ricerche.class;
    }

    @Override
    public void delete(Ricerche entity) {

	ricercheDAO.delete(entity);
    }

    @Override
    public List<Ricerche> findAll(Integer firstResult, Integer maxResult) {

	return ricercheDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Ricerche findById(PkId id) {

	return ricercheDAO.findById(id);
    }

    @Override
    public void insert(Ricerche entity) {

	if (validateEntity(entity)) {
	    ricercheDAO.insert(entity);
	}
    }

    @Override
    public void update(Ricerche entity) {

	if (validateEntity(entity)) {
	    ricercheDAO.update(entity);
	}
    }

    @Override
    public List<Ricerche> findByFilter(Integer codiceresponsabile, String chiave) {

	return ricercheDAO.findByFilter(codiceresponsabile, chiave);
    }
}
