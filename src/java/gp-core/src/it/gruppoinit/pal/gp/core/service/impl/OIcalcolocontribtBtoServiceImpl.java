package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OIcalcolocontribtBtoDAO;
import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribtBto;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.OIcalcolocontribtBtoService;

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
public class OIcalcolocontribtBtoServiceImpl extends BaseServiceImpl<OIcalcolocontribtBto, PkId> implements OIcalcolocontribtBtoService {

    private OIcalcolocontribtBtoDAO oicalcolocontribtbtoDAO;

    @Autowired
    public void setOIcalcolocontribtBtoDAO(OIcalcolocontribtBtoDAO oicalcolocontribtbtoDAO) {

	this.oicalcolocontribtbtoDAO = oicalcolocontribtbtoDAO;
    }

    @Override
    protected Class<OIcalcolocontribtBto> getEntityClass() {

	return OIcalcolocontribtBto.class;
    }

    @Override
    public List<OIcalcolocontribtBto> findAll(Integer firstResult, Integer maxResult) {

	return oicalcolocontribtbtoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OIcalcolocontribtBto entity) {

	if (validateEntity(entity)) {
	    oicalcolocontribtbtoDAO.insert(entity);
	}
    }

    @Override
    public OIcalcolocontribtBto findById(PkId id) {

	return oicalcolocontribtbtoDAO.findById(id);
    }

    @Override
    public void update(OIcalcolocontribtBto entity) {

	if (validateEntity(entity)) {
	    oicalcolocontribtbtoDAO.update(entity);
	}
    }

    @Override
    public void delete(OIcalcolocontribtBto entity) {

	if (isDeleteAllowed(entity)) {
	    oicalcolocontribtbtoDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OIcalcolocontribtBto entity) {

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
