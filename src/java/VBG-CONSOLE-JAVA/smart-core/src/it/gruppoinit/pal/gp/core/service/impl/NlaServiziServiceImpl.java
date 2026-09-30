package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.NlaServiziDAO;
import it.gruppoinit.pal.gp.core.domain.NlaServizi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.NlaServiziService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author fabrizioc
 */
@Service
public class NlaServiziServiceImpl extends BaseServiceImpl<NlaServizi, PkId> implements NlaServiziService {

    private NlaServiziDAO nlaserviziDAO;

    @Autowired
    public void setNlaServiziDAO(NlaServiziDAO nlaserviziDAO) {

	this.nlaserviziDAO = nlaserviziDAO;
    }

    @Override
    protected Class<NlaServizi> getEntityClass() {

	return NlaServizi.class;
    }

    @Override
    public List<NlaServizi> findAll(Integer firstResult, Integer maxResult) {

	return nlaserviziDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(NlaServizi entity) {

	if (validateEntity(entity)) {
	    nlaserviziDAO.insert(entity);
	}
    }

    @Override
    public NlaServizi findById(PkId id) {

	return nlaserviziDAO.findById(id);
    }

    @Override
    public void update(NlaServizi entity) {

	if (validateEntity(entity)) {
	    nlaserviziDAO.update(entity);
	}
    }

    @Override
    public void delete(NlaServizi entity) {

	if (isDeleteAllowed(entity)) {
	    nlaserviziDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(NlaServizi entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getFoArjServizis().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "FO_ARJ_SERVIZI", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
