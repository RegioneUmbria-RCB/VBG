package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.domain.OConfigurazione;
import it.gruppoinit.pal.gp.core.domain.OConfigurazioneId;
import it.gruppoinit.pal.gp.core.service.OConfigurazioneService;

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
public class OConfigurazioneServiceImpl extends BaseServiceImpl<OConfigurazione, OConfigurazioneId> implements OConfigurazioneService {

    private OConfigurazioneDAO oconfigurazioneDAO;

    @Autowired
    public void setOConfigurazioneDAO(OConfigurazioneDAO oconfigurazioneDAO) {

	this.oconfigurazioneDAO = oconfigurazioneDAO;
    }

    @Override
    protected Class<OConfigurazione> getEntityClass() {

	return OConfigurazione.class;
    }

    @Override
    public List<OConfigurazione> findAll(Integer firstResult, Integer maxResult) {

	return oconfigurazioneDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OConfigurazione entity) {

	if (validateEntity(entity)) {
	    oconfigurazioneDAO.insert(entity);
	}
    }

    @Override
    public OConfigurazione findById(OConfigurazioneId id) {

	return oconfigurazioneDAO.findById(id);
    }

    @Override
    public void update(OConfigurazione entity) {

	if (validateEntity(entity)) {
	    oconfigurazioneDAO.update(entity);
	}
    }

    @Override
    public void delete(OConfigurazione entity) {

	if (isDeleteAllowed(entity)) {
	    oconfigurazioneDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OConfigurazione entity) {

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
