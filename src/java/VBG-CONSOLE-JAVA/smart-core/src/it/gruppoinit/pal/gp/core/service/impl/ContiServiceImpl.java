/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ContiDAO;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.ContiService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucap
 * 
 */
@Service
public class ContiServiceImpl extends BaseServiceImpl<Conti, PkId> implements ContiService {

    private ContiDAO contiDAO;

    @Autowired
    public void setContiDAO(ContiDAO contiDAO) {

	this.contiDAO = contiDAO;
    }

    @Override
    protected Class<Conti> getEntityClass() {

	return Conti.class;
    }

    @Override
    public void delete(Conti entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    contiDAO.delete(entity);
	}
	// §§§END§§§
    }

    protected boolean isDeleteAllowed(Conti entity) {

	boolean delete = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!entity.getEndoContis().isEmpty()) {
	    delete = false;
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ENDO_CONTI", null));
	}
	if (!entity.getAlberoContis().isEmpty()) {
	    delete = false;
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ALBERO_CONTI", null));
	}
	if (!entity.getMercatiContis().isEmpty()) {
	    delete = false;
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "MERCATI_CONTI", null));
	}
	if (!entity.getMercatiDContis().isEmpty()) {
	    delete = false;
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "MERCATI_D_CONTI", null));
	}
	if (!entity.getMercatiConfigurazionesContoDefault().isEmpty()) {
	    delete = false;
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "MERCATI_CONFIGURAZIONE", null));
	}
	if (!entity.getMercatiConfigurazionesContoInteressi().isEmpty()) {
	    delete = false;
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "MERCATI_CONFIGURAZIONE", null));
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return delete;
    }

    @Override
    public List<Conti> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return contiDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Conti findById(PkId id) {

	// §§§BEGIN§§§
	return contiDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Conti entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    contiDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public void update(Conti entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    contiDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public List<Conti> findByDescrizione(String descrizione) {

	// §§§BEGIN§§§
	return contiDAO.findByDescrizione(descrizione);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
