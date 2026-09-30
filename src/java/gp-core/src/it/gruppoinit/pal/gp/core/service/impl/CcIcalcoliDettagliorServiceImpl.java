package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoliDettagliorDAO;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoliDettaglior;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcIcalcoliDettagliorService;

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
public class CcIcalcoliDettagliorServiceImpl extends BaseServiceImpl<CcIcalcoliDettaglior, PkId> implements CcIcalcoliDettagliorService {

    private CcIcalcoliDettagliorDAO ccicalcolidettagliorDAO;

    @Autowired
    public void setCcIcalcoliDettagliorDAO(CcIcalcoliDettagliorDAO ccicalcolidettagliorDAO) {

	this.ccicalcolidettagliorDAO = ccicalcolidettagliorDAO;
    }

    @Override
    protected Class<CcIcalcoliDettaglior> getEntityClass() {

	return CcIcalcoliDettaglior.class;
    }

    @Override
    public List<CcIcalcoliDettaglior> findAll(Integer firstResult, Integer maxResult) {

	return ccicalcolidettagliorDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcIcalcoliDettaglior entity) {

	if (validateEntity(entity)) {
	    ccicalcolidettagliorDAO.insert(entity);
	}
    }

    @Override
    public CcIcalcoliDettaglior findById(PkId id) {

	return ccicalcolidettagliorDAO.findById(id);
    }

    @Override
    public void update(CcIcalcoliDettaglior entity) {

	if (validateEntity(entity)) {
	    ccicalcolidettagliorDAO.update(entity);
	}
    }

    @Override
    public void delete(CcIcalcoliDettaglior entity) {

	if (isDeleteAllowed(entity)) {
	    ccicalcolidettagliorDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcIcalcoliDettaglior entity) {

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
