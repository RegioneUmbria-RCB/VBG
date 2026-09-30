package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.RiCaricheDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.RiCariche;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.RiCaricheService;

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
public class RiCaricheServiceImpl extends BaseServiceImpl<RiCariche, String> implements RiCaricheService {

    private RiCaricheDAO ricaricheDAO;

    @Autowired
    public void setRiCaricheDAO(RiCaricheDAO ricaricheDAO) {

	this.ricaricheDAO = ricaricheDAO;
    }

    @Override
    protected Class<RiCariche> getEntityClass() {

	return RiCariche.class;
    }

    @Override
    public List<RiCariche> findAll(Integer firstResult, Integer maxResult) {

	return ricaricheDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(RiCariche entity) {

	if (validateEntity(entity)) {
	    ricaricheDAO.insert(entity);
	}
    }

    @Override
    public RiCariche findById(String id) {

	return ricaricheDAO.findById(id);
    }

    @Override
    public void update(RiCariche entity) {

	if (validateEntity(entity)) {
	    ricaricheDAO.update(entity);
	}
    }

    @Override
    public void delete(RiCariche entity) {

	if (isDeleteAllowed(entity)) {
	    ricaricheDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(RiCariche entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<RiCariche> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResults) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.startsWith("descrizione", textToSearch));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return ricaricheDAO.findByFilterTable(ft, firstResult, maxResults);
    }
}
