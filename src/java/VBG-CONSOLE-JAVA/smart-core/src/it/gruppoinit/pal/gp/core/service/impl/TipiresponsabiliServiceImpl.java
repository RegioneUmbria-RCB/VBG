/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipiresponsabiliDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipiresponsabili;
import it.gruppoinit.pal.gp.core.service.TipiresponsabiliService;

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
public class TipiresponsabiliServiceImpl extends BaseServiceImpl<Tipiresponsabili, PkId> implements TipiresponsabiliService {

    private TipiresponsabiliDAO tipiresponsabiliDAO;

    @Autowired
    public void setTipiresponsabiliDAO(TipiresponsabiliDAO tipiresponsabiliDAO) {

	this.tipiresponsabiliDAO = tipiresponsabiliDAO;
    }

    @Override
    protected Class<Tipiresponsabili> getEntityClass() {

	return Tipiresponsabili.class;
    }

    @Override
    public void delete(Tipiresponsabili entity) {

	if (isDeleteAllowed(entity)) {
	    tipiresponsabiliDAO.delete(entity);
	}
    }

    @Override
    public List<Tipiresponsabili> findAll(Integer firstResult, Integer maxResult) {

	return tipiresponsabiliDAO.findAll(null, null);
    }

    @Override
    public Tipiresponsabili findById(PkId id) {

	return tipiresponsabiliDAO.findById(id);
    }

    @Override
    public void insert(Tipiresponsabili entity) {

	if (validateEntity(entity)) {
	    tipiresponsabiliDAO.insert(entity);
	}
    }

    @Override
    public void update(Tipiresponsabili entity) {

	if (validateEntity(entity)) {
	    tipiresponsabiliDAO.update(entity);
	}
    }

    protected boolean isDeleteAllowed(Tipiresponsabili entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Set<Responsabili> responsabili = entity.getResponsabili();
	if (!responsabili.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "RESPONSABILI", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
