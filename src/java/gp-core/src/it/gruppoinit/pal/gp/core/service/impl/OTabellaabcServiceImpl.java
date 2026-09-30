package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OTabellaabcDAO;
import it.gruppoinit.pal.gp.core.domain.OTabellaabc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.OTabellaabcService;

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
public class OTabellaabcServiceImpl extends BaseServiceImpl<OTabellaabc, PkId> implements OTabellaabcService {

    private OTabellaabcDAO otabellaabcDAO;

    @Autowired
    public void setOTabellaabcDAO(OTabellaabcDAO otabellaabcDAO) {

	this.otabellaabcDAO = otabellaabcDAO;
    }

    @Override
    protected Class<OTabellaabc> getEntityClass() {

	return OTabellaabc.class;
    }

    @Override
    public List<OTabellaabc> findAll(Integer firstResult, Integer maxResult) {

	return otabellaabcDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OTabellaabc entity) {

	if (validateEntity(entity)) {
	    otabellaabcDAO.insert(entity);
	}
    }

    @Override
    public OTabellaabc findById(PkId id) {

	return otabellaabcDAO.findById(id);
    }

    @Override
    public void update(OTabellaabc entity) {

	if (validateEntity(entity)) {
	    otabellaabcDAO.update(entity);
	}
    }

    @Override
    public void delete(OTabellaabc entity) {

	if (isDeleteAllowed(entity)) {
	    otabellaabcDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OTabellaabc entity) {

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
