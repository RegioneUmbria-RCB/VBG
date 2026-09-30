package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.audit.Loggable;
import it.gruppoinit.pal.gp.core.dao.BollCfgMercatiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.BollCfgMercati;
import it.gruppoinit.pal.gp.core.domain.BollCfgMercatiId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.BollCfgMercatiService;

/**
 * 
 * @author
 */
@Loggable(featureName = "bollettazione")
@Service
public class BollCfgMercatiServiceImpl extends BaseServiceImpl<BollCfgMercati, BollCfgMercatiId> implements BollCfgMercatiService {

    private BollCfgMercatiDAO bollcfgmercatiDAO;

    @Autowired
    public void setBollCfgMercatiDAO(BollCfgMercatiDAO bollcfgmercatiDAO) {

	this.bollcfgmercatiDAO = bollcfgmercatiDAO;
    }

    @Override
    protected Class<BollCfgMercati> getEntityClass() {

	return BollCfgMercati.class;
    }

    @Override
    public List<BollCfgMercati> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return bollcfgmercatiDAO.findAll(firstResult, maxResult);	
    }

    @Override
    public void insert(BollCfgMercati entity) {

	if (validateEntity(entity)) {
	    bollcfgmercatiDAO.insert(entity);
	}
    }

    @Override
    public void update(BollCfgMercati entity) {

	if (validateEntity(entity)) {
	    bollcfgmercatiDAO.update(entity);
	}
    }

    @Override
    public void delete(BollCfgMercati entity) {

	if (isDeleteAllowed(entity)) {
	    bollcfgmercatiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(BollCfgMercati entity) {

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
    public BollCfgMercati findById(BollCfgMercatiId id) {

	return bollcfgmercatiDAO.findById(id);
    }

    @Override
    public List<BollCfgMercati> findByBollcfgTipo(Integer codiceBollTipo, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fk_bollcfgtipo_id", codiceBollTipo, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione", "mercati"));
	return bollcfgmercatiDAO.findByFilterTable(ft, firstResult, maxResult);
    }
}
