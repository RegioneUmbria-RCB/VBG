package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OIcalcoloDettagliorDAO;
import it.gruppoinit.pal.gp.core.domain.OIcalcoloDettaglior;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.OIcalcoloDettagliorService;

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
public class OIcalcoloDettagliorServiceImpl extends BaseServiceImpl<OIcalcoloDettaglior, PkId> implements OIcalcoloDettagliorService {

    private OIcalcoloDettagliorDAO oicalcolodettagliorDAO;

    @Autowired
    public void setOIcalcoloDettagliorDAO(OIcalcoloDettagliorDAO oicalcolodettagliorDAO) {

	this.oicalcolodettagliorDAO = oicalcolodettagliorDAO;
    }

    @Override
    protected Class<OIcalcoloDettaglior> getEntityClass() {

	return OIcalcoloDettaglior.class;
    }

    @Override
    public List<OIcalcoloDettaglior> findAll(Integer firstResult, Integer maxResult) {

	return oicalcolodettagliorDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OIcalcoloDettaglior entity) {

	if (validateEntity(entity)) {
	    oicalcolodettagliorDAO.insert(entity);
	}
    }

    @Override
    public OIcalcoloDettaglior findById(PkId id) {

	return oicalcolodettagliorDAO.findById(id);
    }

    @Override
    public void update(OIcalcoloDettaglior entity) {

	if (validateEntity(entity)) {
	    oicalcolodettagliorDAO.update(entity);
	}
    }

    @Override
    public void delete(OIcalcoloDettaglior entity) {

	if (isDeleteAllowed(entity)) {
	    oicalcolodettagliorDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OIcalcoloDettaglior entity) {

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
