package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.SdecomuniassociatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Sdecomuniassociati;
import it.gruppoinit.pal.gp.core.domain.SdecomuniassociatiId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.SdecomuniassociatiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SdecomuniassociatiServiceImpl extends BaseServiceImpl<Sdecomuniassociati, SdecomuniassociatiId> implements SdecomuniassociatiService {

    private SdecomuniassociatiDAO sdecomuniassociatiDAO;

    @Autowired
    public void setSdecomuniassociatiDAO(SdecomuniassociatiDAO sdecomuniassociatiDAO) {

	this.sdecomuniassociatiDAO = sdecomuniassociatiDAO;
    }

    @Override
    public void insert(Sdecomuniassociati entity) {

	if (validateEntity(entity)) {
	    sdecomuniassociatiDAO.insert(entity);
	}
    }

    @Override
    public void update(Sdecomuniassociati entity) {

	if (validateEntity(entity)) {
	    sdecomuniassociatiDAO.update(entity);
	}
    }

    @Override
    public void delete(Sdecomuniassociati entity) {

	if (isDeleteAllowed(entity)) {
	    sdecomuniassociatiDAO.delete(entity);
	}
    }

    @Override
    public List<Sdecomuniassociati> findAll(Integer firstResult, Integer maxResult) {

	throw new RuntimeException("METODO NON IMPLEMENTATO");
	// return sdecomuniassociatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Sdecomuniassociati findById(SdecomuniassociatiId id) {

	return sdecomuniassociatiDAO.findById(id);
    }

    @Override
    protected Class<Sdecomuniassociati> getEntityClass() {

	return Sdecomuniassociati.class;
    }

    @Override
    public List<Sdecomuniassociati> findByIdente(String idente) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idente", idente, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.codicecatastalecomune"));
	return sdecomuniassociatiDAO.findByFilterTable(ft);
    }

    @Override
    public List<Sdecomuniassociati> findByCodiceCatastale(String codicecatastale) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codicecatastalecomune", codicecatastale, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.codicecatastalecomune"));
	return sdecomuniassociatiDAO.findByFilterTable(ft);
    }
}
