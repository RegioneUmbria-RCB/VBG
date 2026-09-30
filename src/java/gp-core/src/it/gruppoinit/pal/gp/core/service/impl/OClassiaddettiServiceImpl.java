package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OClassiaddettiDAO;
import it.gruppoinit.pal.gp.core.domain.OClassiaddetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.OClassiaddettiService;

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
public class OClassiaddettiServiceImpl extends BaseServiceImpl<OClassiaddetti, PkId> implements OClassiaddettiService {

    private OClassiaddettiDAO oclassiaddettiDAO;

    @Autowired
    public void setOClassiaddettiDAO(OClassiaddettiDAO oclassiaddettiDAO) {

	this.oclassiaddettiDAO = oclassiaddettiDAO;
    }

    @Override
    protected Class<OClassiaddetti> getEntityClass() {

	return OClassiaddetti.class;
    }

    @Override
    public List<OClassiaddetti> findAll(Integer firstResult, Integer maxResult) {

	return oclassiaddettiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OClassiaddetti entity) {

	if (validateEntity(entity)) {
	    oclassiaddettiDAO.insert(entity);
	}
    }

    @Override
    public OClassiaddetti findById(PkId id) {

	return oclassiaddettiDAO.findById(id);
    }

    @Override
    public void update(OClassiaddetti entity) {

	if (validateEntity(entity)) {
	    oclassiaddettiDAO.update(entity);
	}
    }

    @Override
    public void delete(OClassiaddetti entity) {

	if (isDeleteAllowed(entity)) {
	    oclassiaddettiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OClassiaddetti entity) {

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
