package it.gruppoinit.pal.gp.core.features.sorteggi.categorie;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.SorteggiCategorie;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class SorteggiCategorieServiceImpl extends BaseServiceImpl<SorteggiCategorie, PkId> implements SorteggiCategorieService {

    private SorteggiCategorieDAO sorteggiCategorieDAO;

    @Autowired
    public void setSorteggiCategorieDAO(SorteggiCategorieDAO sorteggiCategorieDAO) {

	this.sorteggiCategorieDAO = sorteggiCategorieDAO;
    }

    @Override
    protected Class<SorteggiCategorie> getEntityClass() {

	return SorteggiCategorie.class;
    }

    @Override
    public void delete(SorteggiCategorie entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    sorteggiCategorieDAO.delete(entity);
	}
	// §§§END§§§
    }

    @Override
    public List<SorteggiCategorie> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return sorteggiCategorieDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public SorteggiCategorie findById(PkId id) {

	// §§§BEGIN§§§
	return sorteggiCategorieDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(SorteggiCategorie entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    sorteggiCategorieDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public void update(SorteggiCategorie entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    sorteggiCategorieDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public List<SorteggiCategorie> findByFilterTable(FilterTable filterTable) {

	// §§§BEGIN§§§
	return sorteggiCategorieDAO.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    protected boolean isDeleteAllowed(SorteggiCategorie entity) {

	boolean delete = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!entity.getSorteggiList().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "SORTEGGITESTATA", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return delete;
    }

    @Override
    public List<SorteggiCategorie> findBySoftware(String software) {

	return this.sorteggiCategorieDAO.findBySoftware(software);
    }
}
