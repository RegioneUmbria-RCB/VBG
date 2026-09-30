package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipiaperturaDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiapertura;
import it.gruppoinit.pal.gp.core.service.TipiaperturaService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author lucap
 */
@Service
public class TipiaperturaServiceImpl extends BaseServiceImpl<Tipiapertura, PkId> implements TipiaperturaService {

    private TipiaperturaDAO tipiaperturaDAO;

    @Autowired
    public void setTipiaperturaDAO(TipiaperturaDAO tipiaperturaDAO) {

	this.tipiaperturaDAO = tipiaperturaDAO;
    }

    @Override
    protected Class<Tipiapertura> getEntityClass() {

	return Tipiapertura.class;
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_TIPIAPERTURA" })
    public List<Tipiapertura> findAll(Integer firstResult, Integer maxResult) {

	return tipiaperturaDAO.findAll(firstResult, maxResult);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_TIPIAPERTURA" })
    public void insert(Tipiapertura entity) {

	if (validateEntity(entity)) {
	    tipiaperturaDAO.insert(entity);
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_TIPIAPERTURA" })
    public Tipiapertura findById(PkId id) {

	return tipiaperturaDAO.findById(id);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_TIPIAPERTURA" })
    public void update(Tipiapertura entity) {

	if (validateEntity(entity)) {
	    tipiaperturaDAO.update(entity);
	}
    }

    @Override
    @RolesAllowed({ "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_TIPIAPERTURA" })
    public void delete(Tipiapertura entity) {

	if (isDeleteAllowed(entity)) {
	    tipiaperturaDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Tipiapertura entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getTipiorariodettaglios().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIORARIODETTAGLIO", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Tipiapertura> findByDescrizione(Tipiapertura tipiapertura) {

	return tipiaperturaDAO.findByDescrizione(tipiapertura);
    }
}
