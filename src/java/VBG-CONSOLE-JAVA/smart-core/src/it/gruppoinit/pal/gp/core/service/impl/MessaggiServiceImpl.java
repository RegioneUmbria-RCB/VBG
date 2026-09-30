package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MessaggiDAO;
import it.gruppoinit.pal.gp.core.domain.Messaggi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.MessaggiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class MessaggiServiceImpl extends BaseServiceImpl<Messaggi, PkId> implements MessaggiService {

    private MessaggiDAO messaggiDAO;

    @Autowired
    public void setMessaggiDAO(MessaggiDAO messaggiDAO) {

	this.messaggiDAO = messaggiDAO;
    }

    @Override
    protected Class<Messaggi> getEntityClass() {

	return Messaggi.class;
    }

    @Override
    public List<Messaggi> findAll(Integer firstResult, Integer maxResult) {

	return messaggiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Messaggi entity) {

	if (validateEntity(entity)) {
	    messaggiDAO.insert(entity);
	}
    }

    @Override
    public Messaggi findById(PkId id) {

	return messaggiDAO.findById(id);
    }

    @Override
    public void update(Messaggi entity) {

	if (validateEntity(entity)) {
	    messaggiDAO.update(entity);
	}
    }

    @Override
    public void delete(Messaggi entity) {

	if (isDeleteAllowed(entity)) {
	    messaggiDAO.delete(entity);
	}
    }
    /*
     * protected boolean isDeleteAllowed(Messaggi entity) {
     * 
     * boolean delete = true; List<InvalidValue> _ivs = new ArrayList<InvalidValue>(); TODO_validare_la_delete //
     * esempio: // if (entity.getList().size() > 0) { // _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null,
     * null, "NOME_TABELLA", null)); // } if (!_ivs.isEmpty()) { this.throwValidationMessages(_ivs); } return delete; }
     */
}
