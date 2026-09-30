package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipiunitamisuraDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.Tipiunitamisura;
import it.gruppoinit.pal.gp.core.service.TipiunitamisuraService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class TipiunitamisuraServiceImpl extends BaseServiceImpl<it.gruppoinit.pal.gp.core.domain.Tipiunitamisura, PkId> implements
	TipiunitamisuraService {

    private TipiunitamisuraDAO tipiunitamisuraDAO;

    @Autowired
    public void setTipiunitamisuraDAO(TipiunitamisuraDAO tipiunitamisuraDAO) {

	this.tipiunitamisuraDAO = tipiunitamisuraDAO;
    }

    @Override
    protected Class<Tipiunitamisura> getEntityClass() {

	return Tipiunitamisura.class;
    }

    @Override
    public void delete(Tipiunitamisura entity) {

	if (isDeleteAllowed(entity)) {
	    tipiunitamisuraDAO.delete(entity);
	}
    }

    @Override
    public List<Tipiunitamisura> findAll(Integer firstResult, Integer maxResult) {

	return tipiunitamisuraDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Tipiunitamisura findById(PkId id) {

	return tipiunitamisuraDAO.findById(id);
    }

    @Override
    public void insert(Tipiunitamisura entity) {

	if (validateEntity(entity)) {
	    tipiunitamisuraDAO.insert(entity);
	}
    }

    @Override
    public void update(Tipiunitamisura entity) {

	if (validateEntity(entity)) {
	    tipiunitamisuraDAO.update(entity);
	}
    }

    @Override
    public List<Tipiunitamisura> findByFilter(Tipiunitamisura entity) {

	return tipiunitamisuraDAO.findByFilter(entity);
    }

    protected boolean isDeleteAllowed(Tipiunitamisura entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Set<Settori> settoris = entity.getSettoris();
	if (!settoris.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "SETTORI", null));
	}
	if (!entity.getLavoritipiCausalioneris().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "LAVORITIPI_CAUSALIONERI", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
