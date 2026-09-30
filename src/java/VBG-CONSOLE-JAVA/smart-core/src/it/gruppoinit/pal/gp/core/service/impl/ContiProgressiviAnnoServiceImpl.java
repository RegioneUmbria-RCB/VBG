/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ContiProgressiviAnnoDAO;
import it.gruppoinit.pal.gp.core.domain.ContiProgressiviAnno;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.ContiProgressiviAnnoService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class ContiProgressiviAnnoServiceImpl extends BaseServiceImpl<ContiProgressiviAnno, PkId> implements ContiProgressiviAnnoService {

    private ContiProgressiviAnnoDAO contiProgressiviAnnoDAO;

    @Autowired
    public void setContiProgressiviAnnoDAO(ContiProgressiviAnnoDAO contiProgressiviAnnoDAO) {

	this.contiProgressiviAnnoDAO = contiProgressiviAnnoDAO;
    }

    @Override
    protected Class<ContiProgressiviAnno> getEntityClass() {

	return ContiProgressiviAnno.class;
    }

    @Override
    public void delete(ContiProgressiviAnno entity) {

	// §§§BEGIN§§§
	contiProgressiviAnnoDAO.delete(entity);
	// §§§END§§§
    }

    @Override
    public List<ContiProgressiviAnno> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return contiProgressiviAnnoDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public ContiProgressiviAnno findById(PkId id) {

	// §§§BEGIN§§§
	return contiProgressiviAnnoDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(ContiProgressiviAnno entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    contiProgressiviAnnoDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public void update(ContiProgressiviAnno entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    contiProgressiviAnnoDAO.update(entity);
	}
	// §§§END§§§
    }
}
