package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.audit.Loggable;
import it.gruppoinit.pal.gp.core.dao.BollCfgContiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.BollCfgConti;
import it.gruppoinit.pal.gp.core.domain.BollCfgContiId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.BollCfgContiService;

@Loggable(featureName = "bollettazione")
@Service
public class BollCfgContiServiceImpl extends BaseServiceImpl<BollCfgConti, BollCfgContiId> implements BollCfgContiService {

    private BollCfgContiDAO bollCfgContiDAO;

    @Autowired
    public void setBollCfgContiDAO(BollCfgContiDAO bollCfgContiDAO) {

	this.bollCfgContiDAO = bollCfgContiDAO;
    }

    @Override
    protected Class<BollCfgConti> getEntityClass() {

	return BollCfgConti.class;
    }

    @Override
    public List<BollCfgConti> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public void insert(BollCfgConti entity) {

	if (validateEntity(entity)) {
	    bollCfgContiDAO.insert(entity);
	}
    }

    @Override
    public void update(BollCfgConti entity) {

	if (validateEntity(entity)) {
	    bollCfgContiDAO.update(entity);
	}
    }

    @Override
    public void delete(BollCfgConti entity) {

	if (isDeleteAllowed(entity)) {
	    bollCfgContiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(BollCfgConti entity) {

	return true;
    }

    @Override
    public BollCfgConti findById(BollCfgContiId id) {

	return bollCfgContiDAO.findById(id);
    }

    @Override
    public List<BollCfgConti> findByBollCfgTipo(Integer codiceBollTipo, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkBollcfgtipoId", codiceBollTipo, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione", "conto"));
	return bollCfgContiDAO.findByFilterTable(ft, firstResult, maxResult);
    }
}
