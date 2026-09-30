package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoMessaggiDAO;
import it.gruppoinit.pal.gp.core.domain.FoMessaggi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.FoMessaggiService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class FoMessaggiServiceImpl extends BaseServiceImpl<FoMessaggi, PkId> implements FoMessaggiService {

    private FoMessaggiDAO fomessaggiDAO;

    @Autowired
    public void setFoMessaggiDAO(FoMessaggiDAO fomessaggiDAO) {

	this.fomessaggiDAO = fomessaggiDAO;
    }

    @Override
    protected Class<FoMessaggi> getEntityClass() {

	return FoMessaggi.class;
    }

    @Override
    public List<FoMessaggi> findAll(Integer firstResult, Integer maxResult) {

	return fomessaggiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoMessaggi entity) {

	if (validateEntity(entity)) {
	    fomessaggiDAO.insert(entity);
	}
    }

    @Override
    public FoMessaggi findById(PkId id) {

	return fomessaggiDAO.findById(id);
    }

    @Override
    public void update(FoMessaggi entity) {

	if (validateEntity(entity)) {
	    fomessaggiDAO.update(entity);
	}
    }

    @Override
    public void delete(FoMessaggi entity) {

	if (isDeleteAllowed(entity)) {
	    fomessaggiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(FoMessaggi entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
