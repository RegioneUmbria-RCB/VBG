package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CategorieeventibaseDAO;
import it.gruppoinit.pal.gp.core.domain.Categorieeventibase;
import it.gruppoinit.pal.gp.core.service.CategorieeventibaseService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class CategorieeventibaseServiceImpl extends BaseServiceImpl<Categorieeventibase, String> implements CategorieeventibaseService {

    private CategorieeventibaseDAO categorieeventibaseDAO;

    @Autowired
    public void setCategorieeventibaseDAO(CategorieeventibaseDAO categorieeventibaseDAO) {

	this.categorieeventibaseDAO = categorieeventibaseDAO;
    }

    @Override
    protected Class<Categorieeventibase> getEntityClass() {

	return Categorieeventibase.class;
    }

    @Override
    public List<Categorieeventibase> findAll(Integer firstResult, Integer maxResult) {

	return categorieeventibaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Categorieeventibase entity) {

	if (validateEntity(entity)) {
	    categorieeventibaseDAO.insert(entity);
	}
    }

    @Override
    public Categorieeventibase findById(String id) {

	return categorieeventibaseDAO.findById(id);
    }

    @Override
    public void update(Categorieeventibase entity) {

	if (validateEntity(entity)) {
	    categorieeventibaseDAO.update(entity);
	}
    }

    @Override
    public void delete(Categorieeventibase entity) {

	if (isDeleteAllowed(entity)) {
	    categorieeventibaseDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Categorieeventibase entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
