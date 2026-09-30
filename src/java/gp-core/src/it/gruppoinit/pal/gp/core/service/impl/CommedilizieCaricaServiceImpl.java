package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.CommedilizieCaricaDAO;
import it.gruppoinit.pal.gp.core.domain.CommedilizieCarica;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CommedilizieCaricaService;

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
public class CommedilizieCaricaServiceImpl extends BaseServiceImpl<CommedilizieCarica, PkId> implements CommedilizieCaricaService {

    private CommedilizieCaricaDAO commediliziecaricaDAO;

    @Autowired
    public void setCommedilizieCaricaDAO(CommedilizieCaricaDAO commediliziecaricaDAO) {

	this.commediliziecaricaDAO = commediliziecaricaDAO;
    }

    @Override
    protected Class<CommedilizieCarica> getEntityClass() {

	return CommedilizieCarica.class;
    }

    @Override
    public List<CommedilizieCarica> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return commediliziecaricaDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(CommedilizieCarica entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    commediliziecaricaDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public CommedilizieCarica findById(PkId id) {

	// §§§BEGIN§§§
	return commediliziecaricaDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(CommedilizieCarica entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    commediliziecaricaDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(CommedilizieCarica entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    commediliziecaricaDAO.delete(entity);
	}
	// §§§END§§§
    }

    protected boolean isDeleteAllowed(CommedilizieCarica entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getCommedilizieAppellos().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "COMMEDILIZIE_APPELLO", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
