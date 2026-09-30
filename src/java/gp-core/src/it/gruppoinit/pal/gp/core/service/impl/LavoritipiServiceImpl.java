package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.LavoritipiDAO;
import it.gruppoinit.pal.gp.core.domain.Lavoricategorie;
import it.gruppoinit.pal.gp.core.domain.Lavoritipi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.LavoritipiService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Riccardo Bocci
 */
@Service
public class LavoritipiServiceImpl extends BaseServiceImpl<Lavoritipi, PkId> implements LavoritipiService {

    private LavoritipiDAO lavoritipiDAO;

    @Autowired
    public void setLavoritipiDAO(LavoritipiDAO lavoritipiDAO) {

	this.lavoritipiDAO = lavoritipiDAO;
    }

    @Override
    protected Class<Lavoritipi> getEntityClass() {

	return Lavoritipi.class;
    }

    @Override
    public List<Lavoritipi> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return lavoritipiDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Lavoritipi entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    lavoritipiDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public Lavoritipi findById(PkId id) {

	// §§§BEGIN§§§
	return lavoritipiDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(Lavoritipi entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    lavoritipiDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(Lavoritipi entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    lavoritipiDAO.delete(entity);
	}
	// §§§END§§§
    }

    protected boolean isDeleteAllowed(Lavoritipi entity) {

	boolean delete = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getLavoritipiCausalioneris().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "LAVORITIPI_CAUSALIONERI", null));
	}
	if (entity.getIstanzelavoriTs().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZELAVORI_T", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return delete;
    }

    @Override
    public List<Lavoritipi> findByLavoricategorie(Lavoricategorie categoria) {

	// §§§BEGIN§§§
	return lavoritipiDAO.findByLavoricategorie(categoria);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Lavoritipi> findByDescrizione(String descrizione) {

	// §§§BEGIN§§§
	return lavoritipiDAO.findByDescrizione(descrizione);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Lavoritipi> findByFilterTable(FilterTable filterTable) {

	// §§§BEGIN§§§
	return lavoritipiDAO.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
