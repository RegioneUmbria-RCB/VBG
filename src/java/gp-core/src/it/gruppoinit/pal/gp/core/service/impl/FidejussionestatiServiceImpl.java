package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.FidejussionestatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Fidejussionestati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FidejussionestatiService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Riccardo Bocci
 */
@Service
public class FidejussionestatiServiceImpl extends BaseServiceImpl<Fidejussionestati, PkId> implements FidejussionestatiService {

    private FidejussionestatiDAO fidejussionestatiDAO;

    @Autowired
    public void setFidejussionestatiDAO(FidejussionestatiDAO fidejussionestatiDAO) {

	this.fidejussionestatiDAO = fidejussionestatiDAO;
    }

    @Override
    protected Class<Fidejussionestati> getEntityClass() {

	return Fidejussionestati.class;
    }

    @Override
    public List<Fidejussionestati> findAll(Integer firstResult, Integer maxResult) {

	return fidejussionestatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Fidejussionestati entity) {

	if (validateEntity(entity)) {
	    fidejussionestatiDAO.insert(entity);
	}
    }

    @Override
    public Fidejussionestati findById(PkId id) {

	return fidejussionestatiDAO.findById(id);
    }

    @Override
    public void update(Fidejussionestati entity) {

	if (validateEntity(entity)) {
	    fidejussionestatiDAO.update(entity);
	}
    }

    @Override
    public void delete(Fidejussionestati entity) {

	if (isDeleteAllowed(entity)) {
	    fidejussionestatiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Fidejussionestati entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getIstanzefidejussionis().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZEFIDEJUSSIONI", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public boolean existRecordsByCurrentSoftware() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), String.class));
	ft.addRestriction(fr);
	return fidejussionestatiDAO.existsRecords(ft);
    }
}
