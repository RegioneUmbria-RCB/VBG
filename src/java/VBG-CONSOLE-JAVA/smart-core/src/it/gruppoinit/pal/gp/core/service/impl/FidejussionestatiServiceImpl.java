package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FidejussionestatiDAO;
import it.gruppoinit.pal.gp.core.domain.Fidejussionestati;
import it.gruppoinit.pal.gp.core.domain.PkId;
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
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
