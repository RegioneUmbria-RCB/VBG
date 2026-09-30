package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AnagrafemercatipresenzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafemercatipresenze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafemercatipresenzeService;

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
public class AnagrafemercatipresenzeServiceImpl extends BaseServiceImpl<Anagrafemercatipresenze, PkId> implements AnagrafemercatipresenzeService {

    private AnagrafemercatipresenzeDAO anagrafemercatipresenzeDAO;

    @Autowired
    public void setAnagrafemercatipresenzeDAO(AnagrafemercatipresenzeDAO anagrafemercatipresenzeDAO) {

	this.anagrafemercatipresenzeDAO = anagrafemercatipresenzeDAO;
    }

    @Override
    protected Class<Anagrafemercatipresenze> getEntityClass() {

	return Anagrafemercatipresenze.class;
    }

    @Override
    public List<Anagrafemercatipresenze> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return anagrafemercatipresenzeDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Anagrafemercatipresenze entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    anagrafemercatipresenzeDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public Anagrafemercatipresenze findById(PkId id) {

	// §§§BEGIN§§§
	return anagrafemercatipresenzeDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(Anagrafemercatipresenze entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    anagrafemercatipresenzeDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(Anagrafemercatipresenze entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    anagrafemercatipresenzeDAO.delete(entity);
	}
	// §§§END§§§
    }

    protected boolean isDeleteAllowed(Anagrafemercatipresenze entity) {

	boolean delete = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return delete;
    }

    @Override
    public List<Anagrafemercatipresenze> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	filterTable.addRestriction(fr);
	return anagrafemercatipresenzeDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }
}
