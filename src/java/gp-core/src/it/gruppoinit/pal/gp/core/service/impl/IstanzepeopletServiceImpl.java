package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzepeopletDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzepeoplet;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.IstanzepeopletService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IstanzepeopletServiceImpl extends BaseServiceImpl<Istanzepeoplet, PkId> implements IstanzepeopletService {

    private IstanzepeopletDAO istanzepeopletDAO;

    @Autowired
    public void setIstanzepeopletDAO(IstanzepeopletDAO istanzepeopletDAO) {

	this.istanzepeopletDAO = istanzepeopletDAO;
    }

    @Override
    protected Class<Istanzepeoplet> getEntityClass() {

	return Istanzepeoplet.class;
    }

    @Override
    public void delete(Istanzepeoplet entity) {

	istanzepeopletDAO.delete(entity);
    }

    @Override
    public List<Istanzepeoplet> findAll(Integer firstResult, Integer maxResult) {

	return istanzepeopletDAO.findAll(null, null);
    }

    @Override
    public Istanzepeoplet findById(PkId id) {

	return istanzepeopletDAO.findById(id);
    }

    @Override
    public void insert(Istanzepeoplet entity) {

	if (validateEntity(entity)) {
	    istanzepeopletDAO.insert(entity);
	}
    }

    @Override
    public void update(Istanzepeoplet entity) {

	if (validateEntity(entity)) {
	    istanzepeopletDAO.update(entity);
	}
    }

    @Override
    public List<Istanzepeoplet> findIstanzapeoletByIstanza(Istanze istanze) {

	return istanzepeopletDAO.findIstanzapeoletByIstanza(istanze);
    }

    @Override
    public List<Istanzepeoplet> findByFilterTable(FilterTable filterTable) {

	return istanzepeopletDAO.findByFilterTable(filterTable);
    }
}
