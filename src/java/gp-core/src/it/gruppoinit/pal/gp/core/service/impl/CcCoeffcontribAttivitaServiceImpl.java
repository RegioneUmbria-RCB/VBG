package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcCoeffcontribAttivitaDAO;
import it.gruppoinit.pal.gp.core.domain.CcCoeffcontribAttivita;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcCoeffcontribAttivitaService;

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
public class CcCoeffcontribAttivitaServiceImpl extends BaseServiceImpl<CcCoeffcontribAttivita, PkId> implements CcCoeffcontribAttivitaService {

    private CcCoeffcontribAttivitaDAO cccoeffcontribattivitaDAO;

    @Autowired
    public void setCcCoeffcontribAttivitaDAO(CcCoeffcontribAttivitaDAO cccoeffcontribattivitaDAO) {

	this.cccoeffcontribattivitaDAO = cccoeffcontribattivitaDAO;
    }

    @Override
    protected Class<CcCoeffcontribAttivita> getEntityClass() {

	return CcCoeffcontribAttivita.class;
    }

    @Override
    public List<CcCoeffcontribAttivita> findAll(Integer firstResult, Integer maxResult) {

	return cccoeffcontribattivitaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcCoeffcontribAttivita entity) {

	if (validateEntity(entity)) {
	    cccoeffcontribattivitaDAO.insert(entity);
	}
    }

    @Override
    public CcCoeffcontribAttivita findById(PkId id) {

	return cccoeffcontribattivitaDAO.findById(id);
    }

    @Override
    public void update(CcCoeffcontribAttivita entity) {

	if (validateEntity(entity)) {
	    cccoeffcontribattivitaDAO.update(entity);
	}
    }

    @Override
    public void delete(CcCoeffcontribAttivita entity) {

	if (isDeleteAllowed(entity)) {
	    cccoeffcontribattivitaDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcCoeffcontribAttivita entity) {

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
