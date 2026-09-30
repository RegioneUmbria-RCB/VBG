package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.StiliDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Stili;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.StiliService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class StiliServiceImpl extends BaseServiceImpl<Stili, Integer> implements StiliService {

    private StiliDAO stiliDAO;

    @Autowired
    public void setStiliDAO(StiliDAO stiliDAO) {

	this.stiliDAO = stiliDAO;
    }

    @Override
    protected Class<Stili> getEntityClass() {

	return Stili.class;
    }

    @Override
    public List<Stili> findAll(Integer firstResult, Integer maxResult) {

	return stiliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Stili entity) {

	throw new NotImplementedException("Il metodo insert non è implementato");
    }

    @Override
    public Stili findById(Integer id) {

	return stiliDAO.findById(id);
    }

    @Override
    public void update(Stili entity) {

	throw new NotImplementedException("Il metodo update non è implementato");
    }

    @Override
    public void delete(Stili entity) {

	throw new NotImplementedException("Il metodo delete non è implementato");
    }

    @Override
    public Stili findByNome(String nome) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("stNomefile", nome, String.class));
	filterTable.addRestriction(restriction);
	List<Stili> list = stiliDAO.findByFilterTable(filterTable);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }
}
