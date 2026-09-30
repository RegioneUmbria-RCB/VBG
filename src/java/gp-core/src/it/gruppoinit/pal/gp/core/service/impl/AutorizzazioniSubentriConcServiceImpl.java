package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AutorizzazioniSubentriConcDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentriConc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriConcService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorizzazioniSubentriConcServiceImpl extends BaseServiceImpl<AutorizzazioniSubentriConc, PkId> implements
	AutorizzazioniSubentriConcService {

    private AutorizzazioniSubentriConcDAO autorizzazioniSubentriConcDAO;

    @Autowired
    public void setAutorizzazioniSubentriConcDAO(AutorizzazioniSubentriConcDAO autorizzazioniSubentriConcDAO) {

	this.autorizzazioniSubentriConcDAO = autorizzazioniSubentriConcDAO;
    }

    @Override
    public void insert(AutorizzazioniSubentriConc entity) {

	if (validateEntity(entity)) {
	    autorizzazioniSubentriConcDAO.insert(entity);
	}
    }

    @Override
    public void update(AutorizzazioniSubentriConc entity) {

	if (validateEntity(entity)) {
	    autorizzazioniSubentriConcDAO.update(entity);
	}
    }

    @Override
    public void delete(AutorizzazioniSubentriConc entity) {

	if (isDeleteAllowed(entity)) {
	    autorizzazioniSubentriConcDAO.delete(entity);
	}
    }

    @Override
    public List<AutorizzazioniSubentriConc> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public AutorizzazioniSubentriConc findById(PkId id) {

	return autorizzazioniSubentriConcDAO.findById(id);
    }

    @Override
    protected Class<AutorizzazioniSubentriConc> getEntityClass() {

	return AutorizzazioniSubentriConc.class;
    }

    @Override
    public List<AutorizzazioniSubentriConc> findByIdSubentro(Integer idSubentro) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("autorizzazioniSubentriId", idSubentro, Integer.class));
	ft.addRestriction(filterRestriction);
	ft.addOrder(FilterUtils.orderAsc("descrizione", "mercati"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "mercatiUso"));
	ft.addOrder(FilterUtils.orderAsc("codiceposteggio", "mercatiD"));
	return autorizzazioniSubentriConcDAO.findByFilterTable(ft);
    }

    @Override
    public List<AutorizzazioniSubentriConc> findByIdSubentroAutCollegata(Integer idSubentroAutCollegata) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("autorizzazioniSubentriByFkAutconcAutcollId", idSubentroAutCollegata, Integer.class));
	ft.addRestriction(filterRestriction);
	ft.addOrder(FilterUtils.orderAsc("descrizione", "mercati"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "mercatiUso"));
	ft.addOrder(FilterUtils.orderAsc("codiceposteggio", "mercatiD"));
	return autorizzazioniSubentriConcDAO.findByFilterTable(ft);
    }
}
