package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OrariaperturaDAO;
import it.gruppoinit.pal.gp.core.domain.Orariapertura;
import it.gruppoinit.pal.gp.core.domain.Orariaperturatestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.OrariaperturaService;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author lucap
 */
@Service
public class OrariaperturaServiceImpl extends BaseServiceImpl<Orariapertura, PkId> implements OrariaperturaService {

    private OrariaperturaDAO orariaperturaDAO;

    @Autowired
    public void setOrariaperturaDAO(OrariaperturaDAO orariaperturaDAO) {

	this.orariaperturaDAO = orariaperturaDAO;
    }

    @Override
    protected Class<Orariapertura> getEntityClass() {

	return Orariapertura.class;
    }

    @Override
    public List<Orariapertura> findAll(Integer firstResult, Integer maxResult) {

	return orariaperturaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Orariapertura entity) {

	if (validateEntity(entity)) {
	    orariaperturaDAO.insert(entity);
	}
    }

    @Override
    public Orariapertura findById(PkId id) {

	return orariaperturaDAO.findById(id);
    }

    @Override
    public void update(Orariapertura entity) {

	if (validateEntity(entity)) {
	    orariaperturaDAO.update(entity);
	}
    }

    @Override
    public void delete(Orariapertura entity) {

	if (isDeleteAllowed(entity)) {
	    orariaperturaDAO.delete(entity);
	}
    }

    @Override
    public void deleteByOrariaperturatestata(Orariaperturatestata orariaperturatestata) {

	Set<Orariapertura> list = orariaperturatestata.getOrariaperturas();
	for (Orariapertura orariapertura : list) {
	    this.delete(orariapertura);
	}
	orariaperturaDAO.flush();
    }
}
