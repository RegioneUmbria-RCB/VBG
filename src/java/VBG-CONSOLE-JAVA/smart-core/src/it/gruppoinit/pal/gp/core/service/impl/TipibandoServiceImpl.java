/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipibandoDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibando;
import it.gruppoinit.pal.gp.core.service.TipibandoService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class TipibandoServiceImpl extends BaseServiceImpl<Tipibando, PkId> implements TipibandoService {

    private TipibandoDAO tipibandoDAO;

    @Autowired
    public void setTipibandoDAO(TipibandoDAO tipibandoDAO) {

	this.tipibandoDAO = tipibandoDAO;
    }

    @Override
    protected Class<Tipibando> getEntityClass() {

	return Tipibando.class;
    }

    @Override
    public void delete(Tipibando entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    tipibandoDAO.delete(entity);
	}
	// §§§END§§§
    }

    @Override
    public List<Tipibando> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return tipibandoDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Tipibando findById(PkId id) {

	// §§§BEGIN§§§
	return tipibandoDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Tipibando entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipibandoDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public void update(Tipibando entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipibandoDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public List<Tipibando> findActiveTipibando() {

	// §§§BEGIN§§§
	return tipibandoDAO.findActiveTipibando();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private void dataIntegration(Tipibando entity) {

	if (entity == null) {
	    throw new RuntimeException("Non si può inserire/aggiornare tipo bando nulla");
	}
	if (entity.getFlagMultiintervento() == null) {
	    entity.setFlagMultiintervento(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    protected boolean isDeleteAllowed(Tipibando entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
