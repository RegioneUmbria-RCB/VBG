package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ElencoinpsbaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Elencoinpsbase;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ElencoinpsbaseService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElencoinpsbaseServiceImpl extends BaseServiceImpl<Elencoinpsbase, String> implements ElencoinpsbaseService {

    private ElencoinpsbaseDAO elencoinpsbaseDAO;

    @Autowired
    public void setElencoinpsbaseDAO(ElencoinpsbaseDAO elencoinpsbaseDAO) {

	this.elencoinpsbaseDAO = elencoinpsbaseDAO;
    }

    @Override
    public void insert(Elencoinpsbase entity) {

	if (validateEntity(entity)) {
	    elencoinpsbaseDAO.insert(entity);
	}
    }

    @Override
    public void update(Elencoinpsbase entity) {

	if (validateEntity(entity)) {
	    elencoinpsbaseDAO.update(entity);
	}
    }

    @Override
    public void delete(Elencoinpsbase entity) {

	if (isDeleteAllowed(entity)) {
	    elencoinpsbaseDAO.delete(entity);
	}
    }

    @Override
    public List<Elencoinpsbase> findAll(Integer firstResult, Integer maxResult) {

	return elencoinpsbaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Elencoinpsbase findById(String id) {

	return elencoinpsbaseDAO.findById(id);
    }

    @Override
    public List<Elencoinpsbase> findByDescrizione(String descrizione) {

	return elencoinpsbaseDAO.findByDescrizione(descrizione);
    }

    @Override
    protected Class<Elencoinpsbase> getEntityClass() {

	return Elencoinpsbase.class;
    }

    @Override
    protected Elencoinpsbase customBindDomainObject(Elencoinpsbase entity) {

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
	List<Elencoinpsbase> results = elencoinpsbaseDAO.findByFilterTable(ft);
	if (results.size() == 1) {
	    return results.get(0);
	}
	return null;
    }
}
