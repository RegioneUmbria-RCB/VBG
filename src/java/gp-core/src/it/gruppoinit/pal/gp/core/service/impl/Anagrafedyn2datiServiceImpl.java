package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.Anagrafedyn2datiDAO;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2datiId;
import it.gruppoinit.pal.gp.core.service.Anagrafedyn2datiService;

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
public class Anagrafedyn2datiServiceImpl extends BaseServiceImpl<Anagrafedyn2dati, Anagrafedyn2datiId> implements Anagrafedyn2datiService {

    private Anagrafedyn2datiDAO anagrafedyn2datiDAO;

    @Autowired
    public void setAnagrafedyn2datiDAO(Anagrafedyn2datiDAO anagrafedyn2datiDAO) {

	this.anagrafedyn2datiDAO = anagrafedyn2datiDAO;
    }

    @Override
    protected Class<Anagrafedyn2dati> getEntityClass() {

	return Anagrafedyn2dati.class;
    }

    @Override
    public List<Anagrafedyn2dati> findAll(Integer firstResult, Integer maxResult) {

	return anagrafedyn2datiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Anagrafedyn2dati entity) {

	if (validateEntity(entity)) {
	    anagrafedyn2datiDAO.insert(entity);
	}
    }

    @Override
    public Anagrafedyn2dati findById(Anagrafedyn2datiId id) {

	return anagrafedyn2datiDAO.findById(id);
    }

    @Override
    public void update(Anagrafedyn2dati entity) {

	if (validateEntity(entity)) {
	    anagrafedyn2datiDAO.update(entity);
	}
    }

    @Override
    public void delete(Anagrafedyn2dati entity) {

	if (isDeleteAllowed(entity)) {
	    anagrafedyn2datiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Anagrafedyn2dati entity) {

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
