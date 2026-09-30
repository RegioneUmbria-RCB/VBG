package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OInterventiDAO;
import it.gruppoinit.pal.gp.core.domain.OInterventi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.OInterventiService;

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
public class OInterventiServiceImpl extends BaseServiceImpl<OInterventi, PkId> implements OInterventiService {

    private OInterventiDAO ointerventiDAO;

    @Autowired
    public void setOInterventiDAO(OInterventiDAO ointerventiDAO) {

	this.ointerventiDAO = ointerventiDAO;
    }

    @Override
    protected Class<OInterventi> getEntityClass() {

	return OInterventi.class;
    }

    @Override
    public List<OInterventi> findAll(Integer firstResult, Integer maxResult) {

	return ointerventiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OInterventi entity) {

	if (validateEntity(entity)) {
	    ointerventiDAO.insert(entity);
	}
    }

    @Override
    public OInterventi findById(PkId id) {

	return ointerventiDAO.findById(id);
    }

    @Override
    public void update(OInterventi entity) {

	if (validateEntity(entity)) {
	    ointerventiDAO.update(entity);
	}
    }

    @Override
    public void delete(OInterventi entity) {

	if (isDeleteAllowed(entity)) {
	    ointerventiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OInterventi entity) {

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
