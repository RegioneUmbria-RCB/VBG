package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.LavoricategorieDAO;
import it.gruppoinit.pal.gp.core.domain.Lavoricategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.LavoricategorieService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class LavoricategorieServiceImpl extends BaseServiceImpl<Lavoricategorie, PkId> implements LavoricategorieService {

    private LavoricategorieDAO lavoricategorieDAO;

    @Autowired
    public void setLavoricategorieDAO(LavoricategorieDAO lavoricategorieDAO) {

	this.lavoricategorieDAO = lavoricategorieDAO;
    }

    @Override
    protected Class<Lavoricategorie> getEntityClass() {

	return Lavoricategorie.class;
    }

    @Override
    public List<Lavoricategorie> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return lavoricategorieDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Lavoricategorie entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    lavoricategorieDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public Lavoricategorie findById(PkId id) {

	// §§§BEGIN§§§
	return lavoricategorieDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(Lavoricategorie entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    lavoricategorieDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(Lavoricategorie entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    lavoricategorieDAO.delete(entity);
	}
	// §§§END§§§
    }

    protected boolean isDeleteAllowed(Lavoricategorie entity) {

	boolean delete = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getLavoritipis().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "LAVORITIPI", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return delete;
    }

    @Override
    public List<Lavoricategorie> findByFilterTable(FilterTable filterTable) {

	// §§§BEGIN§§§
	return lavoricategorieDAO.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
