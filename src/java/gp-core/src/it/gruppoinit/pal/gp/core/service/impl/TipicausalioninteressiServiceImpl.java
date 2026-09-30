package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.TipicausalioninteressiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioninteressi;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.TipicausalioninteressiService;

/**
 * 
 * @author
 */
@Service
public class TipicausalioninteressiServiceImpl extends BaseServiceImpl<Tipicausalioninteressi, PkId> implements TipicausalioninteressiService {

    private TipicausalioninteressiDAO tipicausalioninteressiDAO;

    @Autowired
    public void setTipicausalioninteressiDAO(TipicausalioninteressiDAO tipicausalioninteressiDAO) {

	this.tipicausalioninteressiDAO = tipicausalioninteressiDAO;
    }

    @Override
    protected Class<Tipicausalioninteressi> getEntityClass() {

	return Tipicausalioninteressi.class;
    }

    @Override
    public List<Tipicausalioninteressi> findAll(Integer firstResult, Integer maxResult) {

	return tipicausalioninteressiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Tipicausalioninteressi entity) {

	if (validateEntity(entity) && isInserOrUpdateAllowed(entity)) {
	    tipicausalioninteressiDAO.insert(entity);
	}
    }

    @Override
    public Tipicausalioninteressi findById(PkId id) {

	return tipicausalioninteressiDAO.findById(id);
    }

    @Override
    public void update(Tipicausalioninteressi entity) {

	if (validateEntity(entity) && isInserOrUpdateAllowed(entity)) {
	    tipicausalioninteressiDAO.update(entity);
	}
    }

    @Override
    public void delete(Tipicausalioninteressi entity) {

	if (isDeleteAllowed(entity)) {
	    tipicausalioninteressiDAO.delete(entity);
	}
    }

    @Override
    public List<Tipicausalioninteressi> findByTipicausalioneri(Tipicausalioneri tipicausalioneri) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.codice", tipicausalioneri.getId().getCodice(), "tipicausalioneri", Integer.class));
	filterTable.addRestriction(filterRestriction);
	filterTable.addOrder(FilterUtils.orderAsc("ggritardopagamento"));
	return tipicausalioninteressiDAO.findByFilterTable(filterTable);
    }

    private boolean isInserOrUpdateAllowed(Tipicausalioninteressi entity) {

	boolean isInsertOrUpdate = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getGgritardopagamento() == null) {
	    _ivs.add(new InvalidValue("service_error.tipicausalioneriinteressi.no_giorni_ritardo", null, null, null, null));
	}
	if (entity.getPercentuale() == null) {
	    _ivs.add(new InvalidValue("service_error.tipicausalioneriinteressi.no_percentuale_di_mora", null, null, null, null));
	}
	if (!_ivs.isEmpty()) {
	    isInsertOrUpdate = false;
	    this.throwValidationMessages(_ivs);
	}
	return isInsertOrUpdate;
    }
    //    protected boolean isDeleteAllowed(Tipicausalioninteressi entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }

    @Override
    public List<Tipicausalioninteressi> findByIdEGGpassati(Integer id, Integer ggPassati) {

	return this.tipicausalioninteressiDAO.findByIdEGGpassati(id, ggPassati);
    }
}
