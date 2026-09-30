package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoloDcontribattivDAO;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontribattiv;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcIcalcoloDcontribattivService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class CcIcalcoloDcontribattivServiceImpl extends BaseServiceImpl<CcIcalcoloDcontribattiv, PkId> implements CcIcalcoloDcontribattivService {

    private CcIcalcoloDcontribattivDAO ccicalcolodcontribattivDAO;

    @Autowired
    public void setCcIcalcoloDcontribattivDAO(CcIcalcoloDcontribattivDAO ccicalcolodcontribattivDAO) {

	this.ccicalcolodcontribattivDAO = ccicalcolodcontribattivDAO;
    }

    @Override
    protected Class<CcIcalcoloDcontribattiv> getEntityClass() {

	return CcIcalcoloDcontribattiv.class;
    }

    @Override
    public List<CcIcalcoloDcontribattiv> findAll(Integer firstResult, Integer maxResult) {

	return ccicalcolodcontribattivDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcIcalcoloDcontribattiv entity) {

	if (validateEntity(entity)) {
	    ccicalcolodcontribattivDAO.insert(entity);
	}
    }

    @Override
    public CcIcalcoloDcontribattiv findById(PkId id) {

	return ccicalcolodcontribattivDAO.findById(id);
    }

    @Override
    public void update(CcIcalcoloDcontribattiv entity) {

	if (validateEntity(entity)) {
	    ccicalcolodcontribattivDAO.update(entity);
	}
    }

    @Override
    public void delete(CcIcalcoloDcontribattiv entity) {

	if (isDeleteAllowed(entity)) {
	    ccicalcolodcontribattivDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcIcalcoloDcontribattiv entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO _validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
