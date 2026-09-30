package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OIcalcoloDettagliotDAO;
import it.gruppoinit.pal.gp.core.domain.OIcalcoloDettagliot;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.OIcalcoloDettagliotService;

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
public class OIcalcoloDettagliotServiceImpl extends BaseServiceImpl<OIcalcoloDettagliot, PkId> implements OIcalcoloDettagliotService {

    private OIcalcoloDettagliotDAO oicalcolodettagliotDAO;

    @Autowired
    public void setOIcalcoloDettagliotDAO(OIcalcoloDettagliotDAO oicalcolodettagliotDAO) {

	this.oicalcolodettagliotDAO = oicalcolodettagliotDAO;
    }

    @Override
    protected Class<OIcalcoloDettagliot> getEntityClass() {

	return OIcalcoloDettagliot.class;
    }

    @Override
    public List<OIcalcoloDettagliot> findAll(Integer firstResult, Integer maxResult) {

	return oicalcolodettagliotDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OIcalcoloDettagliot entity) {

	if (validateEntity(entity)) {
	    oicalcolodettagliotDAO.insert(entity);
	}
    }

    @Override
    public OIcalcoloDettagliot findById(PkId id) {

	return oicalcolodettagliotDAO.findById(id);
    }

    @Override
    public void update(OIcalcoloDettagliot entity) {

	if (validateEntity(entity)) {
	    oicalcolodettagliotDAO.update(entity);
	}
    }

    @Override
    public void delete(OIcalcoloDettagliot entity) {

	if (isDeleteAllowed(entity)) {
	    oicalcolodettagliotDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OIcalcoloDettagliot entity) {

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
