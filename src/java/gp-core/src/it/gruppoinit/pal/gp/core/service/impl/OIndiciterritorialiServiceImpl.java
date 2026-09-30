package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OIndiciterritorialiDAO;
import it.gruppoinit.pal.gp.core.domain.OIndiciterritoriali;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.OIndiciterritorialiService;

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
public class OIndiciterritorialiServiceImpl extends BaseServiceImpl<OIndiciterritoriali, PkId> implements OIndiciterritorialiService {

    private OIndiciterritorialiDAO oindiciterritorialiDAO;

    @Autowired
    public void setOIndiciterritorialiDAO(OIndiciterritorialiDAO oindiciterritorialiDAO) {

	this.oindiciterritorialiDAO = oindiciterritorialiDAO;
    }

    @Override
    protected Class<OIndiciterritoriali> getEntityClass() {

	return OIndiciterritoriali.class;
    }

    @Override
    public List<OIndiciterritoriali> findAll(Integer firstResult, Integer maxResult) {

	return oindiciterritorialiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OIndiciterritoriali entity) {

	if (validateEntity(entity)) {
	    oindiciterritorialiDAO.insert(entity);
	}
    }

    @Override
    public OIndiciterritoriali findById(PkId id) {

	return oindiciterritorialiDAO.findById(id);
    }

    @Override
    public void update(OIndiciterritoriali entity) {

	if (validateEntity(entity)) {
	    oindiciterritorialiDAO.update(entity);
	}
    }

    @Override
    public void delete(OIndiciterritoriali entity) {

	if (isDeleteAllowed(entity)) {
	    oindiciterritorialiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OIndiciterritoriali entity) {

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
