package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.audit.Loggable;
import it.gruppoinit.pal.gp.core.dao.BollCfgCausalioneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.BollCfgCausalioneri;
import it.gruppoinit.pal.gp.core.domain.BollCfgCausalioneriId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.BollCfgCausalioneriService;

/**
 * 
 * @author
 */
@Loggable(featureName = "bollettazione")
@Service
public class BollCfgCausalioneriServiceImpl extends BaseServiceImpl<BollCfgCausalioneri, BollCfgCausalioneriId>
	implements BollCfgCausalioneriService {

    private BollCfgCausalioneriDAO bollcfgcausalioneriDAO;

    @Autowired
    public void setBollCfgCausalioneriDAO(BollCfgCausalioneriDAO bollcfgcausalioneriDAO) {

	this.bollcfgcausalioneriDAO = bollcfgcausalioneriDAO;
    }

    @Override
    protected Class<BollCfgCausalioneri> getEntityClass() {

	return BollCfgCausalioneri.class;
    }

    @Override
    public List<BollCfgCausalioneri> findAll(Integer firstResult, Integer maxResult) {

	return bollcfgcausalioneriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(BollCfgCausalioneri entity) {

	if (validateEntity(entity)) {
	    bollcfgcausalioneriDAO.insert(entity);
	}
    }

    @Override
    public void update(BollCfgCausalioneri entity) {

	if (validateEntity(entity)) {
	    bollcfgcausalioneriDAO.update(entity);
	}
    }

    @Override
    public void delete(BollCfgCausalioneri entity) {

	if (isDeleteAllowed(entity)) {
	    bollcfgcausalioneriDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(BollCfgCausalioneri entity) {

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
    public BollCfgCausalioneri findById(BollCfgCausalioneriId id) {

	return bollcfgcausalioneriDAO.findById(id);
    }

    @Override
    public List<BollCfgCausalioneri> findByBollCfgTipo(Integer bollcfgTipo, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", bollcfgTipo, "bollCfgTipo", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("coDescrizione", "tipicausalioneri"));
	return bollcfgcausalioneriDAO.findByFilterTable(ft, firstResult, maxResult);
    }
}
