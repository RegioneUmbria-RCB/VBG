package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatistradarioDAO;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.Mercatistradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.MercatistradarioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class MercatistradarioServiceImpl extends BaseServiceImpl<Mercatistradario, PkId> implements MercatistradarioService {

    private MercatistradarioDAO mercatistradarioDAO;

    @Autowired
    public void setMercatistradarioDAO(MercatistradarioDAO mercatistradarioDAO) {

	this.mercatistradarioDAO = mercatistradarioDAO;
    }

    @Override
    protected Class<Mercatistradario> getEntityClass() {

	return Mercatistradario.class;
    }

    @Override
    public List<Mercatistradario> findAll(Integer firstResult, Integer maxResult) {

	return mercatistradarioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Mercatistradario entity) {

	if (validateEntity(entity)) {
	    mercatistradarioDAO.insert(entity);
	}
    }

    @Override
    public Mercatistradario findById(PkId id) {

	return mercatistradarioDAO.findById(id);
    }

    @Override
    public void update(Mercatistradario entity) {

	if (validateEntity(entity)) {
	    mercatistradarioDAO.update(entity);
	}
    }

    @Override
    public void delete(Mercatistradario entity) {

	if (isDeleteAllowed(entity)) {
	    mercatistradarioDAO.delete(entity);
	}
    }

    @Override
    public List<Mercatistradario> findByMercato(Mercati mercati) {

	return mercatistradarioDAO.findByMercato(mercati);
    }
}
