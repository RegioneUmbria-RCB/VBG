package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TempLinkallegatiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TempLinkallegati;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.TempLinkallegatiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TempLinkallegatiServiceImpl extends BaseServiceImpl<TempLinkallegati, PkId> implements TempLinkallegatiService {

    private TempLinkallegatiDAO tempLinkallegatiDAO;

    @Autowired
    public void setTempLinkallegatiDAO(TempLinkallegatiDAO tempLinkallegatiDAO) {

	this.tempLinkallegatiDAO = tempLinkallegatiDAO;
    }

    @Override
    public void insert(TempLinkallegati entity) {

	if (validateEntity(entity)) {
	    tempLinkallegatiDAO.insert(entity);
	}
    }

    @Override
    public void update(TempLinkallegati entity) {

	if (validateEntity(entity)) {
	    tempLinkallegatiDAO.update(entity);
	}
    }

    @Override
    public List<TempLinkallegati> findByUuid(String uuid) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("uuid", uuid, String.class));
	ft.addRestriction(fr);
	//ft.addOrder(FilterUtils.or);
	return tempLinkallegatiDAO.findByFilterTable(ft);
    }

    @Override
    public void delete(TempLinkallegati entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<TempLinkallegati> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public TempLinkallegati findById(PkId id) {

	throw new NotImplementedException();
    }

    @Override
    protected Class<TempLinkallegati> getEntityClass() {

	return tempLinkallegatiDAO.getEntityClass();
    }
}
