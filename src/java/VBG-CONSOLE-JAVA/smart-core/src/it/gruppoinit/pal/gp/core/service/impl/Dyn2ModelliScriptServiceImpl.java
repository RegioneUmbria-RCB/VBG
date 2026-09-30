package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.Dyn2ModelliScriptDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2ModelliScript;
import it.gruppoinit.pal.gp.core.domain.Dyn2ModelliScriptId;
import it.gruppoinit.pal.gp.core.service.Dyn2ModelliScriptService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 
 * @author
 */
@Service
public class Dyn2ModelliScriptServiceImpl extends BaseServiceImpl<Dyn2ModelliScript, Dyn2ModelliScriptId> implements Dyn2ModelliScriptService {

    private Dyn2ModelliScriptDAO dyn2modelliscriptDAO;

    @Autowired
    public void setDyn2ModelliScriptDAO(Dyn2ModelliScriptDAO dyn2modelliscriptDAO) {

	this.dyn2modelliscriptDAO = dyn2modelliscriptDAO;
    }

    @Override
    protected Class<Dyn2ModelliScript> getEntityClass() {

	return Dyn2ModelliScript.class;
    }

    @Override
    public List<Dyn2ModelliScript> findAll(Integer firstResult, Integer maxResult) {

	return dyn2modelliscriptDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Dyn2ModelliScript entity) {

	if (validateEntity(entity)) {
	    dyn2modelliscriptDAO.insert(entity);
	}
    }

    @Override
    public Dyn2ModelliScript findById(Dyn2ModelliScriptId id) {

	return dyn2modelliscriptDAO.findById(id);
    }

    @Override
    public void update(Dyn2ModelliScript entity) {

	if (validateEntity(entity)) {
	    dyn2modelliscriptDAO.update(entity);
	}
    }

    @Override
    public void delete(Dyn2ModelliScript entity) {

	if (isDeleteAllowed(entity)) {
	    dyn2modelliscriptDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Dyn2ModelliScript entity) {

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

    @Override
    public Dyn2ModelliScript findByModelloAndEvento(Integer codiceModello, String name) {

	Assert.notNull(codiceModello, "Il codice modello non può essere vuoto.");
	Assert.hasLength(name, "L'evento non può essere vuoto.");
	Dyn2ModelliScriptId id = new Dyn2ModelliScriptId(codiceModello, name);
	return this.findById(id);
    }
}
