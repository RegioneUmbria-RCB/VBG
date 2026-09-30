package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.LeggitipiDAO;
import it.gruppoinit.pal.gp.core.domain.Leggi;
import it.gruppoinit.pal.gp.core.domain.Leggitipi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.LeggitipiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeggitipiServiceImpl extends BaseServiceImpl<Leggitipi, PkId> implements LeggitipiService {

    private LeggitipiDAO leggitipiDAO;

    @Autowired
    public void setLeggitipiDAO(LeggitipiDAO leggitipiDAO) {

	this.leggitipiDAO = leggitipiDAO;
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_LEGGITIPI" })
    @Override
    public void delete(Leggitipi entity) {

	if (isDeleteAllowed(entity)) {
	    leggitipiDAO.delete(entity);
	}
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_LEGGITIPI" })
    @Override
    public List<Leggitipi> findAll(Integer firstResult, Integer maxResult) {

	return leggitipiDAO.findAll(firstResult, maxResult);
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_LEGGITIPI" })
    @Override
    public Leggitipi findById(PkId id) {

	return leggitipiDAO.findById(id);
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_LEGGITIPI" })
    @Override
    public void insert(Leggitipi entity) {

	if (validateEntity(entity)) {
	    leggitipiDAO.insert(entity);
	}
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_LEGGITIPI" })
    @Override
    public void update(Leggitipi entity) {

	if (validateEntity(entity)) {
	    leggitipiDAO.update(entity);
	}
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_LEGGITIPI" })
    @Override
    public List<Leggitipi> findByFilter(Leggitipi entity) {

	return leggitipiDAO.findByFilter(entity);
    }

    protected boolean isDeleteAllowed(Leggitipi entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Set<Leggi> leggis = entity.getLeggis();
	if (leggis != null && leggis.size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "LEGGI", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public Class<Leggitipi> getEntityClass() {

	return Leggitipi.class;
    }
}
