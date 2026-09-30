package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.BachecalavorocercaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Bachecalavorocerca;
import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.BachecalavorocercaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class BachecalavorocercaServiceImpl extends BaseServiceImpl<Bachecalavorocerca, PkId> implements BachecalavorocercaService {

    private BachecalavorocercaDAO bachecalavorocercaDAO;

    @Autowired
    public void setBachecalavorocercaDAO(BachecalavorocercaDAO bachecalavorocercaDAO) {

	this.bachecalavorocercaDAO = bachecalavorocercaDAO;
    }

    @Override
    protected Class<Bachecalavorocerca> getEntityClass() {

	return Bachecalavorocerca.class;
    }

    @Override
    public List<Bachecalavorocerca> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return bachecalavorocercaDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Bachecalavorocerca entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    bachecalavorocercaDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public Bachecalavorocerca findById(PkId id) {

	// §§§BEGIN§§§
	return bachecalavorocercaDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(Bachecalavorocerca entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    bachecalavorocercaDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(Bachecalavorocerca entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    bachecalavorocercaDAO.delete(entity);
	}
	// §§§END§§§
    }

    protected boolean isDeleteAllowed(Faq entity) {

	boolean delete = true;
	return delete;
    }

    @Override
    public List<Bachecalavorocerca> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	filterTable.addRestriction(fr);
	return bachecalavorocercaDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }
}
