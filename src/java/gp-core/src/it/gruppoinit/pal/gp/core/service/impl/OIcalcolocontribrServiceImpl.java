package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OIcalcolocontribrDAO;
import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribr;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.OIcalcolocontribrService;

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
public class OIcalcolocontribrServiceImpl extends BaseServiceImpl<OIcalcolocontribr, PkId> implements OIcalcolocontribrService {

    private OIcalcolocontribrDAO oicalcolocontribrDAO;

    @Autowired
    public void setOIcalcolocontribrDAO(OIcalcolocontribrDAO oicalcolocontribrDAO) {

	this.oicalcolocontribrDAO = oicalcolocontribrDAO;
    }

    @Override
    protected Class<OIcalcolocontribr> getEntityClass() {

	return OIcalcolocontribr.class;
    }

    @Override
    public List<OIcalcolocontribr> findAll(Integer firstResult, Integer maxResult) {

	return oicalcolocontribrDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OIcalcolocontribr entity) {

	if (validateEntity(entity)) {
	    oicalcolocontribrDAO.insert(entity);
	}
    }

    @Override
    public OIcalcolocontribr findById(PkId id) {

	return oicalcolocontribrDAO.findById(id);
    }

    @Override
    public void update(OIcalcolocontribr entity) {

	if (validateEntity(entity)) {
	    oicalcolocontribrDAO.update(entity);
	}
    }

    @Override
    public void delete(OIcalcolocontribr entity) {

	if (isDeleteAllowed(entity)) {
	    oicalcolocontribrDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OIcalcolocontribr entity) {

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
