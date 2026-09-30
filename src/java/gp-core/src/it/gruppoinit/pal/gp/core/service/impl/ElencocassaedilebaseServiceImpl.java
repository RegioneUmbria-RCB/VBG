package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ElencocassaedilebaseDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Elencocassaedilebase;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ElencocassaedilebaseService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElencocassaedilebaseServiceImpl extends BaseServiceImpl<Elencocassaedilebase, String> implements ElencocassaedilebaseService {

    private ElencocassaedilebaseDAO elencocassaedilebaseDAO;

    @Autowired
    public void setElencocassaedilebaseDAO(ElencocassaedilebaseDAO elencocassaedilebaseDAO) {

	this.elencocassaedilebaseDAO = elencocassaedilebaseDAO;
    }

    @Override
    public void insert(Elencocassaedilebase entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(Elencocassaedilebase entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(Elencocassaedilebase entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<Elencocassaedilebase> findAll(Integer firstResult, Integer maxResult) {

	return elencocassaedilebaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Elencocassaedilebase findById(String id) {

	return elencocassaedilebaseDAO.findById(id);
    }

    @Override
    public List<Elencocassaedilebase> findByDescrizione(String descrizione) {

	return elencocassaedilebaseDAO.findByDescrizione(descrizione);
    }

    @Override
    protected Class<Elencocassaedilebase> getEntityClass() {

	return elencocassaedilebaseDAO.getEntityClass();
    }

    @Override
    protected Elencocassaedilebase customBindDomainObject(Elencocassaedilebase entity) {

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
	List<Elencocassaedilebase> results = elencocassaedilebaseDAO.findByFilterTable(ft);
	if (results.size() == 1) {
	    return results.get(0);
	}
	return null;
    }
}
