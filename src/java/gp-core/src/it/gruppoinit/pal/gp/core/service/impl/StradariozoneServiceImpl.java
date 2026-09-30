package it.gruppoinit.pal.gp.core.service.impl;

/**
 * @author gianpaolot
 */
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.StradariozoneDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradariozone;
import it.gruppoinit.pal.gp.core.service.StradariozoneService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StradariozoneServiceImpl extends BaseServiceImpl<Stradariozone, PkId> implements StradariozoneService {

    private StradariozoneDAO stradariozoneDAO;

    @Autowired
    public void setStradariozoneDAO(StradariozoneDAO stradariozoneDAO) {

	this.stradariozoneDAO = stradariozoneDAO;
    }

    @Override
    protected Class<Stradariozone> getEntityClass() {

	return Stradariozone.class;
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_STRADARIOZONE" })
    @Override
    public List<Stradariozone> findByFilter(Stradariozone entity) {

	return stradariozoneDAO.findByFilter(entity);
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_STRADARIOZONE" })
    @Override
    public void delete(Stradariozone entity) {

	if (isDeleteAllowed(entity)) {
	    stradariozoneDAO.delete(entity);
	}
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_STRADARIOZONE" })
    @Override
    public List<Stradariozone> findAll(Integer firstResult, Integer maxResult) {

	return stradariozoneDAO.findAll(firstResult, maxResult);
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_STRADARIOZONE" })
    @Override
    public Stradariozone findById(PkId id) {

	return stradariozoneDAO.findById(id);
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_STRADARIOZONE" })
    @Override
    public void insert(Stradariozone entity) {

	if (validateEntity(entity)) {
	    stradariozoneDAO.insert(entity);
	}
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_STRADARIOZONE" })
    @Override
    public void update(Stradariozone entity) {

	if (validateEntity(entity)) {
	    stradariozoneDAO.update(entity);
	}
    }

    protected boolean isDeleteAllowed(Stradariozone entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!entity.getStradarios().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "STRADARIO", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
