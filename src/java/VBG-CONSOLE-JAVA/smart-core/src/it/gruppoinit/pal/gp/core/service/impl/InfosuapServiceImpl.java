package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.InfosuapDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Infosuap;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.InfosuapService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author lucap
 */
@Service
public class InfosuapServiceImpl extends BaseServiceImpl<Infosuap, PkId> implements InfosuapService {

    private InfosuapDAO infosuapDAO;

    @Autowired
    public void setInfosuapDAO(InfosuapDAO infosuapDAO) {

	this.infosuapDAO = infosuapDAO;
    }

    @Override
    protected Class<Infosuap> getEntityClass() {

	return Infosuap.class;
    }

    @Override
    public List<Infosuap> findAll(Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("titolo"));
	return infosuapDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public void insert(Infosuap entity) {

	if (validateEntity(entity)) {
	    infosuapDAO.insert(entity);
	}
    }

    @Override
    public Infosuap findById(PkId id) {

	return infosuapDAO.findById(id);
    }

    @Override
    public void update(Infosuap entity) {

	if (validateEntity(entity)) {
	    infosuapDAO.update(entity);
	}
    }

    @Override
    public void delete(Infosuap entity) {

	if (isDeleteAllowed(entity)) {
	    infosuapDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Infosuap entity) {

	boolean delete = true;
	return delete;
    }
}
