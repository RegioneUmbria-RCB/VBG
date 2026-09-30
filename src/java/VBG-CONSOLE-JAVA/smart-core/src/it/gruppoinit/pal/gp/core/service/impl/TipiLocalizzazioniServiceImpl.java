package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipiLocalizzazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipiLocalizzazioni;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.TipiLocalizzazioniService;

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
public class TipiLocalizzazioniServiceImpl extends BaseServiceImpl<TipiLocalizzazioni, PkId> implements TipiLocalizzazioniService {

    private TipiLocalizzazioniDAO tipilocalizzazioniDAO;

    @Autowired
    public void setTipiLocalizzazioniDAO(TipiLocalizzazioniDAO tipilocalizzazioniDAO) {

	this.tipilocalizzazioniDAO = tipilocalizzazioniDAO;
    }

    @Override
    protected Class<TipiLocalizzazioni> getEntityClass() {

	return TipiLocalizzazioni.class;
    }

    @Override
    public List<TipiLocalizzazioni> findAll(Integer firstResult, Integer maxResult) {

	return tipilocalizzazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(TipiLocalizzazioni entity) {

	if (validateEntity(entity)) {
	    tipilocalizzazioniDAO.insert(entity);
	}
    }

    @Override
    public TipiLocalizzazioni findById(PkId id) {

	return tipilocalizzazioniDAO.findById(id);
    }

    @Override
    public void update(TipiLocalizzazioni entity) {

	if (validateEntity(entity)) {
	    tipilocalizzazioniDAO.update(entity);
	}
    }

    @Override
    public void delete(TipiLocalizzazioni entity) {

	if (isDeleteAllowed(entity)) {
	    tipilocalizzazioniDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(TipiLocalizzazioni entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<TipiLocalizzazioni> findByDescrizione(String descrizione, Integer firstResult, Integer maxResults) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("descrizione", descrizione));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return tipilocalizzazioniDAO.findByFilterTable(ft, firstResult, maxResults);
    }
}
