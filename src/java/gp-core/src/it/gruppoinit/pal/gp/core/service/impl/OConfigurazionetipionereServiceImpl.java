package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OConfigurazionetipionereDAO;
import it.gruppoinit.pal.gp.core.domain.OConfigurazionetipionere;
import it.gruppoinit.pal.gp.core.domain.OConfigurazionetipionereId;
import it.gruppoinit.pal.gp.core.service.OConfigurazionetipionereService;

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
public class OConfigurazionetipionereServiceImpl extends BaseServiceImpl<OConfigurazionetipionere, OConfigurazionetipionereId> implements
	OConfigurazionetipionereService {

    private OConfigurazionetipionereDAO oconfigurazionetipionereDAO;

    @Autowired
    public void setOConfigurazionetipionereDAO(OConfigurazionetipionereDAO oconfigurazionetipionereDAO) {

	this.oconfigurazionetipionereDAO = oconfigurazionetipionereDAO;
    }

    @Override
    protected Class<OConfigurazionetipionere> getEntityClass() {

	return OConfigurazionetipionere.class;
    }

    @Override
    public List<OConfigurazionetipionere> findAll(Integer firstResult, Integer maxResult) {

	return oconfigurazionetipionereDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OConfigurazionetipionere entity) {

	if (validateEntity(entity)) {
	    oconfigurazionetipionereDAO.insert(entity);
	}
    }

    @Override
    public OConfigurazionetipionere findById(OConfigurazionetipionereId id) {

	return oconfigurazionetipionereDAO.findById(id);
    }

    @Override
    public void update(OConfigurazionetipionere entity) {

	if (validateEntity(entity)) {
	    oconfigurazionetipionereDAO.update(entity);
	}
    }

    @Override
    public void delete(OConfigurazionetipionere entity) {

	if (isDeleteAllowed(entity)) {
	    oconfigurazionetipionereDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OConfigurazionetipionere entity) {

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
