package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcIcalcolotcontributoRiduzDAO;
import it.gruppoinit.pal.gp.core.domain.CcIcalcolotcontributoRiduz;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcIcalcolotcontributoRiduzService;

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
public class CcIcalcolotcontributoRiduzServiceImpl extends BaseServiceImpl<CcIcalcolotcontributoRiduz, PkId> implements
	CcIcalcolotcontributoRiduzService {

    private CcIcalcolotcontributoRiduzDAO ccicalcolotcontributoriduzDAO;

    @Autowired
    public void setCcIcalcolotcontributoRiduzDAO(CcIcalcolotcontributoRiduzDAO ccicalcolotcontributoriduzDAO) {

	this.ccicalcolotcontributoriduzDAO = ccicalcolotcontributoriduzDAO;
    }

    @Override
    protected Class<CcIcalcolotcontributoRiduz> getEntityClass() {

	return CcIcalcolotcontributoRiduz.class;
    }

    @Override
    public List<CcIcalcolotcontributoRiduz> findAll(Integer firstResult, Integer maxResult) {

	return ccicalcolotcontributoriduzDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcIcalcolotcontributoRiduz entity) {

	if (validateEntity(entity)) {
	    ccicalcolotcontributoriduzDAO.insert(entity);
	}
    }

    @Override
    public CcIcalcolotcontributoRiduz findById(PkId id) {

	return ccicalcolotcontributoriduzDAO.findById(id);
    }

    @Override
    public void update(CcIcalcolotcontributoRiduz entity) {

	if (validateEntity(entity)) {
	    ccicalcolotcontributoriduzDAO.update(entity);
	}
    }

    @Override
    public void delete(CcIcalcolotcontributoRiduz entity) {

	if (isDeleteAllowed(entity)) {
	    ccicalcolotcontributoriduzDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcIcalcolotcontributoRiduz entity) {

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
