package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ElenchiprofessionalibaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Elenchiprofessionalibase;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ElenchiprofessionalibaseService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class ElenchiprofessionalibaseServiceImpl extends BaseServiceImpl<Elenchiprofessionalibase, Integer> implements
	ElenchiprofessionalibaseService {

    private ElenchiprofessionalibaseDAO elenchiprofessionalibaseDAO;

    @Autowired
    public void setElenchiprofessionalibaseDAO(ElenchiprofessionalibaseDAO elenchiprofessionalibaseDAO) {

	this.elenchiprofessionalibaseDAO = elenchiprofessionalibaseDAO;
    }

    @Override
    protected Class<Elenchiprofessionalibase> getEntityClass() {

	return Elenchiprofessionalibase.class;
    }

    @Override
    public List<Elenchiprofessionalibase> findAll(Integer firstResult, Integer maxResult) {

	return elenchiprofessionalibaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Elenchiprofessionalibase entity) {

	if (validateEntity(entity)) {
	    elenchiprofessionalibaseDAO.insert(entity);
	}
    }

    @Override
    public Elenchiprofessionalibase findById(Integer id) {

	return elenchiprofessionalibaseDAO.findById(id);
    }

    @Override
    public void update(Elenchiprofessionalibase entity) {

	if (validateEntity(entity)) {
	    elenchiprofessionalibaseDAO.update(entity);
	}
    }

    @Override
    public void delete(Elenchiprofessionalibase entity) {

	if (isDeleteAllowed(entity)) {
	    elenchiprofessionalibaseDAO.delete(entity);
	}
    }

    @Override
    public List<Elenchiprofessionalibase> findByFilterTable(FilterTable filterTable) {

	return elenchiprofessionalibaseDAO.findByFilterTable(filterTable);
    }

    // protected boolean isDeleteAllowed(Elenchiprofessionalibase entity) {
    //
    // boolean delete = true;
    // List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    // TODO_validare_la_delete
    // // esempio:
    // // if (entity.getList().size() > 0) {
    // // _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    // // }
    // if (!_ivs.isEmpty()) {
    // this.throwValidationMessages(_ivs);
    // }
    // return delete;
    // }
    @Override
    protected Elenchiprofessionalibase customBindDomainObject(Elenchiprofessionalibase entity) {

	if (entity == null) {
	    return null;
	}
	if (StringUtils.isBlank(entity.getEpDescrizione())) {
	    return null;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("epDescrizione", entity.getEpDescrizione()));
	ft.addRestriction(fr);
	List<Elenchiprofessionalibase> results = elenchiprofessionalibaseDAO.findByFilterTable(ft);
	if (results.size() == 1) {
	    return results.get(0);
	}
	return null;
    }
}
