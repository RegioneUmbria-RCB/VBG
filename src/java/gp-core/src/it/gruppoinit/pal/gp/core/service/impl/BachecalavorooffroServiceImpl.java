package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.BachecalavorooffroDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Bachecalavorooffro;
import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.BachecalavorooffroService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class BachecalavorooffroServiceImpl extends BaseServiceImpl<Bachecalavorooffro, PkId> implements BachecalavorooffroService {

    private BachecalavorooffroDAO bachecalavorooffroDAO;

    @Autowired
    public void setBachecalavorooffroDAO(BachecalavorooffroDAO bachecalavorooffroDAO) {

	this.bachecalavorooffroDAO = bachecalavorooffroDAO;
    }

    @Override
    protected Class<Bachecalavorooffro> getEntityClass() {

	return Bachecalavorooffro.class;
    }

    @Override
    public List<Bachecalavorooffro> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return bachecalavorooffroDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Bachecalavorooffro entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    bachecalavorooffroDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public Bachecalavorooffro findById(PkId id) {

	// §§§BEGIN§§§
	return bachecalavorooffroDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(Bachecalavorooffro entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    bachecalavorooffroDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(Bachecalavorooffro entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    bachecalavorooffroDAO.delete(entity);
	}
	// §§§END§§§
    }

    protected boolean isDeleteAllowed(Faq entity) {

	boolean delete = true;
	return delete;
    }

    @Override
    public List<Bachecalavorooffro> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	filterTable.addRestriction(fr);
	return bachecalavorooffroDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }
}
