package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoRichiesteDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoRichieste;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FoRichiesteService;

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
public class FoRichiesteServiceImpl extends BaseServiceImpl<FoRichieste, PkId> implements FoRichiesteService {

    private FoRichiesteDAO forichiesteDAO;

    @Autowired
    public void setFoRichiesteDAO(FoRichiesteDAO forichiesteDAO) {

	this.forichiesteDAO = forichiesteDAO;
    }

    @Override
    protected Class<FoRichieste> getEntityClass() {

	return FoRichieste.class;
    }

    @Override
    public List<FoRichieste> findAll(Integer firstResult, Integer maxResult) {

	return forichiesteDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoRichieste entity) {

	if (validateEntity(entity)) {
	    forichiesteDAO.insert(entity);
	}
    }

    @Override
    public FoRichieste findById(PkId id) {

	return forichiesteDAO.findById(id);
    }

    @Override
    public void update(FoRichieste entity) {

	if (validateEntity(entity)) {
	    forichiesteDAO.update(entity);
	}
    }

    @Override
    public void delete(FoRichieste entity) {

	if (isDeleteAllowed(entity)) {
	    forichiesteDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(FoRichieste entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<FoRichieste> findByFilterTable(FilterTable filterTable) {

	return forichiesteDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<FoRichieste> findRichiesteNonLette(Integer firstResult, Integer maxResult) {

	FilterTable filterTable = getFilterRichiesteNonLette();
	List<FoRichieste> foRichieste = new ArrayList<FoRichieste>();
	if (null != firstResult && null != maxResult) {
	    foRichieste = forichiesteDAO.findByFilterTable(filterTable, firstResult.intValue(), maxResult.intValue());
	} else {
	    foRichieste = forichiesteDAO.findByFilterTable(filterTable);
	}
	return foRichieste;
    }

    @Override
    public int countRichiesteNonLette() {

	FilterTable filterTable = getFilterRichiesteNonLette();
	return forichiesteDAO.countRecord(filterTable);
    }

    private FilterTable getFilterRichiesteNonLette() {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.isNull("flagLetto"));
	fr.addFilterField(FilterUtils.equals("flagLetto", Boolean.FALSE, Boolean.class));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("datarichiesta"));
	return filterTable;
    }
}
