package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ElencoinailbaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Elencoinailbase;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ElencoinailbaseService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElencoinailbaseServiceImpl extends BaseServiceImpl<Elencoinailbase, String> implements ElencoinailbaseService {

    private ElencoinailbaseDAO elencoinailbaseDAO;

    @Autowired
    public void setElencoinailbaseDAO(ElencoinailbaseDAO elencoinailbaseDAO) {

	this.elencoinailbaseDAO = elencoinailbaseDAO;
    }

    @Override
    public void insert(Elencoinailbase entity) {

	if (validateEntity(entity)) {
	    elencoinailbaseDAO.insert(entity);
	}
    }

    @Override
    public void update(Elencoinailbase entity) {

	if (validateEntity(entity)) {
	    elencoinailbaseDAO.update(entity);
	}
    }

    @Override
    public void delete(Elencoinailbase entity) {

	if (isDeleteAllowed(entity)) {
	    elencoinailbaseDAO.delete(entity);
	}
    }

    @Override
    public List<Elencoinailbase> findAll(Integer firstResult, Integer maxResult) {

	return elencoinailbaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Elencoinailbase findById(String id) {

	return elencoinailbaseDAO.findById(id);
    }

    @Override
    public List<Elencoinailbase> findByDescrizione(String descrizione) {

	return elencoinailbaseDAO.findByDescrizione(descrizione);
    }

    @Override
    protected Class<Elencoinailbase> getEntityClass() {

	return Elencoinailbase.class;
    }

    @Override
    protected Elencoinailbase customBindDomainObject(Elencoinailbase entity) {

	if (entity == null) {
	    return null;
	}
	if (StringUtils.isBlank(entity.getDescrizione())) {
	    return null;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("descrizione", entity.getDescrizione()));
	ft.addRestriction(fr);
	List<Elencoinailbase> results = elencoinailbaseDAO.findByFilterTable(ft);
	if (results.size() == 1) {
	    return results.get(0);
	}
	return null;
    }
}
