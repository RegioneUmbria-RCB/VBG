package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.LayoutpaginebaseDAO;
import it.gruppoinit.pal.gp.core.domain.Layoutpaginebase;
import it.gruppoinit.pal.gp.core.service.LayoutpaginebaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class LayoutpaginebaseServiceImpl extends BaseServiceImpl<Layoutpaginebase, String> implements LayoutpaginebaseService {

    private LayoutpaginebaseDAO layoutpaginebaseDAO;

    @Autowired
    public void setLayoutpaginebaseDAO(LayoutpaginebaseDAO layoutpaginebaseDAO) {

	this.layoutpaginebaseDAO = layoutpaginebaseDAO;
    }

    @Override
    protected Class<Layoutpaginebase> getEntityClass() {

	return Layoutpaginebase.class;
    }

    @Override
    public List<Layoutpaginebase> findAll(Integer firstResult, Integer maxResult) {

	return layoutpaginebaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Layoutpaginebase entity) {

	if (validateEntity(entity)) {
	    layoutpaginebaseDAO.insert(entity);
	}
    }

    @Override
    public Layoutpaginebase findById(String id) {

	return layoutpaginebaseDAO.findById(id);
    }

    @Override
    public void update(Layoutpaginebase entity) {

	if (validateEntity(entity)) {
	    layoutpaginebaseDAO.update(entity);
	}
    }

    @Override
    public void delete(Layoutpaginebase entity) {

	if (isDeleteAllowed(entity)) {
	    layoutpaginebaseDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Layoutpaginebase entity) {

	// TODO validare se ci sono record in layoutpagine
	return true;
    }
}
