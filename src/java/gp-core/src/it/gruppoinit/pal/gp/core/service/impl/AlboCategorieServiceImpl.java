package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AlboCategorieDAO;
import it.gruppoinit.pal.gp.core.domain.AlboCategorie;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AlboCategorieService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlboCategorieServiceImpl extends BaseServiceImpl<AlboCategorie, PkId> implements AlboCategorieService {

    private AlboCategorieDAO alboCategorieDAO;

    @Autowired
    public void setAlboCategorieDAO(AlboCategorieDAO alboCategorieDAO) {

	this.alboCategorieDAO = alboCategorieDAO;
    }

    @Override
    protected Class<AlboCategorie> getEntityClass() {

	return AlboCategorie.class;
    }

    @Override
    public void delete(AlboCategorie entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    alboCategorieDAO.delete(entity);
	}
	// §§§END§§§
    }

    protected boolean isDeleteAllowed(AlboCategorie entity) {

	boolean delete = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Set<AlboPubblicazioni> alboPubblicazioniSet = entity.getAlboPubblicazionis();
	if (!alboPubblicazioniSet.isEmpty()) {
	    delete = false;
	    InvalidValue iv = new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ALBO_PUBBLICAZIONI", null);
	    _ivs.add(iv);
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return delete;
    }

    @Override
    public List<AlboCategorie> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return alboCategorieDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public AlboCategorie findById(PkId id) {

	return alboCategorieDAO.findById(id);
    }

    @Override
    public void insert(AlboCategorie entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    alboCategorieDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public void update(AlboCategorie entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    alboCategorieDAO.update(entity);
	}
	// §§§END§§§
    }
}
