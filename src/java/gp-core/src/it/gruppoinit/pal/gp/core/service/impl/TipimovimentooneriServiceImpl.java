package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipimovimentooneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentooneri;
import it.gruppoinit.pal.gp.core.domain.TipimovimentooneriId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.TipimovimentooneriService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class TipimovimentooneriServiceImpl extends BaseServiceImpl<Tipimovimentooneri, TipimovimentooneriId> implements TipimovimentooneriService {

    private TipimovimentooneriDAO tipimovimentooneriDAO;

    @Autowired
    public void setTipimovimentooneriDAO(TipimovimentooneriDAO tipimovimentooneriDAO) {

	this.tipimovimentooneriDAO = tipimovimentooneriDAO;
    }

    @Override
    protected Class<Tipimovimentooneri> getEntityClass() {

	return Tipimovimentooneri.class;
    }

    @Override
    public List<Tipimovimentooneri> findAll(Integer firstResult, Integer maxResult) {

	return tipimovimentooneriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Tipimovimentooneri entity) {

	if (validateEntity(entity) && isInsertOrUpdateAllowed(entity)) {
	    tipimovimentooneriDAO.insert(entity);
	}
    }

    @Override
    public Tipimovimentooneri findById(TipimovimentooneriId id) {

	return tipimovimentooneriDAO.findById(id);
    }

    @Override
    public void update(Tipimovimentooneri entity) {

	if (validateEntity(entity) && isInsertOrUpdateAllowed(entity)) {
	    tipimovimentooneriDAO.update(entity);
	}
    }

    @Override
    public void delete(Tipimovimentooneri entity) {

	if (isDeleteAllowed(entity)) {
	    tipimovimentooneriDAO.delete(entity);
	}
    }

    @Override
    public Tipimovimentooneri findByTipomovimentoAndCausaleOnere(Tipimovimento tipomovimento, Tipicausalioneri tipicausalioneri) {

	if (EntityUtils.getNestedProperty(tipomovimento, "id.tipomovimento") == null) {
	    throw new IllegalArgumentException("Il parametro tipo movimento non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.tipomovimento", tipomovimento.getId().getTipomovimento(), String.class));
	fr.addFilterField(FilterUtils.equals("tipicausalioneri", tipicausalioneri, Tipicausalioneri.class));
	ft.addRestriction(fr);
	List<Tipimovimentooneri> list = tipimovimentooneriDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    private boolean isInsertOrUpdateAllowed(Tipimovimentooneri entity) {

	boolean isAllowed = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getOnericomportamento().getCodicecomportamento().equals(WebConstants.ONERI_COMPORTAMENTO_IMPOSTA_SCADENZA)) {
	    if (entity.getGgscadenza() == null) {
		_ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", Tipimovimentooneri.class, "ggscadenza", entity,
			new Tipimovimentooneri()));
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return isAllowed;
    }

    // protected boolean isDeleteAllowed(Tipimovimentooneri entity) {
    //
    // boolean delete = true;
    // List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    // TODO_validare_la_delete
    // // esempio:
    // // if (entity.getList().size() > 0) {
    // // _ivs.add(new InvalidValue(WebConstants.ALERT_FOREING_KEY, null, null, "NOME_TABELLA", null));
    // // }
    // if (!_ivs.isEmpty()) {
    // this.throwValidationMessages(_ivs);
    // }
    // return delete;
    // }
    @Override
    public List<Tipimovimentooneri> findByTipimovimento(String tipimovimento) {

	if (StringUtils.isBlank(tipimovimento)) {
	    throw new IllegalArgumentException("Il parametro tipo movimento non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.tipomovimento", tipimovimento, String.class));
	ft.addRestriction(fr);
	return tipimovimentooneriDAO.findByFilterTable(ft);
    }
}
