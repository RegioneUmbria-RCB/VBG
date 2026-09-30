package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.Dyn2CampiScriptDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiScript;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiScriptId;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiScriptService;

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
public class Dyn2CampiScriptServiceImpl extends BaseServiceImpl<Dyn2CampiScript, Dyn2CampiScriptId> implements Dyn2CampiScriptService {

    private Dyn2CampiScriptDAO dyn2campiscriptDAO;

    @Autowired
    public void setDyn2CampiScriptDAO(Dyn2CampiScriptDAO dyn2campiscriptDAO) {

	this.dyn2campiscriptDAO = dyn2campiscriptDAO;
    }

    @Override
    protected Class<Dyn2CampiScript> getEntityClass() {

	return Dyn2CampiScript.class;
    }

    @Override
    public List<Dyn2CampiScript> findAll(Integer firstResult, Integer maxResult) {

	return dyn2campiscriptDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Dyn2CampiScript entity) {

	if (validateEntity(entity)) {
	    dyn2campiscriptDAO.insert(entity);
	}
    }

    @Override
    public Dyn2CampiScript findById(Dyn2CampiScriptId id) {

	return dyn2campiscriptDAO.findById(id);
    }

    @Override
    public void update(Dyn2CampiScript entity) {

	if (validateEntity(entity)) {
	    dyn2campiscriptDAO.update(entity);
	}
    }

    @Override
    public void delete(Dyn2CampiScript entity) {

	if (isDeleteAllowed(entity)) {
	    dyn2campiscriptDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Dyn2CampiScript entity) {

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
    public Dyn2CampiScript findByCampoAndEvento(Integer codiceCampo, String evento) {

	Assert.notNull(codiceCampo, "Il codice modello non può essere vuoto.");
	Assert.hasLength(evento, "L'evento non può essere vuoto.");
	Dyn2CampiScriptId id = new Dyn2CampiScriptId(codiceCampo, evento);
	return this.findById(id);
    }
}
