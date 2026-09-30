package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.audit.Loggable;
import it.gruppoinit.pal.gp.core.dao.BollCfgRuoliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.BollCfgRuoli;
import it.gruppoinit.pal.gp.core.domain.BollCfgRuoliId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.BollCfgRuoliService;

/**
 * 
 * @author
 */
@Loggable(featureName = "bollettazione")
@Service
public class BollCfgRuoliServiceImpl extends BaseServiceImpl<BollCfgRuoli, BollCfgRuoliId> implements BollCfgRuoliService {

    private BollCfgRuoliDAO bllcfgruoliDAO;

    @Autowired
    public void setBllCfgRuoliDAO(BollCfgRuoliDAO bllcfgruoliDAO) {

	this.bllcfgruoliDAO = bllcfgruoliDAO;
    }

    @Override
    protected Class<BollCfgRuoli> getEntityClass() {

	return BollCfgRuoli.class;
    }

    @Override
    public List<BollCfgRuoli> findAll(Integer firstResult, Integer maxResult) {

	return bllcfgruoliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(BollCfgRuoli entity) {

	if (validateEntity(entity)) {
	    bllcfgruoliDAO.insert(entity);
	}
    }

    @Override
    public void update(BollCfgRuoli entity) {

	if (validateEntity(entity)) {
	    bllcfgruoliDAO.update(entity);
	}
    }

    @Override
    public void delete(BollCfgRuoli entity) {

	if (isDeleteAllowed(entity)) {
	    bllcfgruoliDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(BollCfgRuoli entity) {

	boolean delete = true;
	//		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		TODO_validare_la_delete
	//		// esempio:
	//		// if (entity.getList().size() > 0) {
	//		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//		// }
	//		if (!_ivs.isEmpty()) {
	//			this.throwValidationMessages(_ivs);
	//		}
	return delete;
    }

    @Override
    public BollCfgRuoli findById(BollCfgRuoliId id) {

	return bllcfgruoliDAO.findById(id);
    }

    @Override
    public List<BollCfgRuoli> findByBollcfgTipo(Integer codiceBollTipo, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceBollTipo, "bollCfgTipo", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ruolo", "ruoli"));
	return bllcfgruoliDAO.findByFilterTable(ft, firstResult, maxResult);
    }
}
