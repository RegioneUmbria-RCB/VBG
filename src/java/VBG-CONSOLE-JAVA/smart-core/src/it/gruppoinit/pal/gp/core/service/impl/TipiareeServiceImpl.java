package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipiareeDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiaree;
import it.gruppoinit.pal.gp.core.service.TipiareeService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipiareeServiceImpl extends BaseServiceImpl<Tipiaree, PkId> implements TipiareeService {

    private TipiareeDAO tipiareeDAO;

    @Autowired
    public void setTipiareeDAO(TipiareeDAO tipiareeDAO) {

	this.tipiareeDAO = tipiareeDAO;
    }

    @Override
    protected Class<Tipiaree> getEntityClass() {

	return Tipiaree.class;
    }

    @Override
    public void delete(Tipiaree entity) {

	if (isDeleteAllowed(entity)) {
	    tipiareeDAO.delete(entity);
	}
    }

    @Override
    public List<Tipiaree> findAll(Integer firstResult, Integer maxResult) {

	return tipiareeDAO.findAll(null, null);
    }

    @Override
    public Tipiaree findById(PkId id) {

	return tipiareeDAO.findById(id);
    }

    @Override
    public void insert(Tipiaree entity) {

	if (validateEntity(entity))
	    tipiareeDAO.insert(entity);
    }

    @Override
    public void update(Tipiaree entity) {

	if (validateEntity(entity))
	    tipiareeDAO.update(entity);
    }

    @Override
    public List<Tipiaree> findByFilter(Tipiaree entity) {

	return tipiareeDAO.findByFilter(entity);
    }

    protected boolean isDeleteAllowed(Tipiaree entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!entity.getArees().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "AREE", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
