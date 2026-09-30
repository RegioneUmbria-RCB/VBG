/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipibandoinputDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibando;
import it.gruppoinit.pal.gp.core.domain.Tipibandoinput;
import it.gruppoinit.pal.gp.core.service.TipibandoinputService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucap
 * 
 */
@Service
public class TipibandoinputServiceImpl extends BaseServiceImpl<Tipibandoinput, PkId> implements TipibandoinputService {

    private TipibandoinputDAO tipibandoinputDAO;

    @Autowired
    public void setTipibandoinputDAO(TipibandoinputDAO tipibandoinputDAO) {

	this.tipibandoinputDAO = tipibandoinputDAO;
    }

    @Override
    protected Class<Tipibandoinput> getEntityClass() {

	return Tipibandoinput.class;
    }

    @Override
    public void delete(Tipibandoinput entity) {

	// §§§BEGIN§§§
	tipibandoinputDAO.delete(entity);
	// §§§END§§§
    }

    @Override
    public List<Tipibandoinput> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return tipibandoinputDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Tipibandoinput findById(PkId id) {

	// §§§BEGIN§§§
	return tipibandoinputDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Tipibandoinput entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    tipibandoinputDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public void update(Tipibandoinput entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    tipibandoinputDAO.update(entity);
	}
	// §§§END§§§
    }

    public List<Tipibandoinput> findTipiBandiInput(Tipibandoinput tipibandoinput) {

	// §§§BEGIN§§§
	List<Tipibandoinput> list = tipibandoinputDAO.findTipiBanInp(tipibandoinput);
	return list;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Tipibandoinput> findByFilterTipobando(Tipibando tipibando) {

	// §§§BEGIN§§§
	return tipibandoinputDAO.findByFilterTipobando(tipibando);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
