package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoloDcontributoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontributo;
import it.gruppoinit.pal.gp.core.domain.CcTipointervento;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CcIcalcoloDcontributoService;

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
public class CcIcalcoloDcontributoServiceImpl extends BaseServiceImpl<CcIcalcoloDcontributo, PkId> implements CcIcalcoloDcontributoService {

    private CcIcalcoloDcontributoDAO ccicalcolodcontributoDAO;

    @Autowired
    public void setCcIcalcoloDcontributoDAO(CcIcalcoloDcontributoDAO ccicalcolodcontributoDAO) {

	this.ccicalcolodcontributoDAO = ccicalcolodcontributoDAO;
    }

    @Override
    protected Class<CcIcalcoloDcontributo> getEntityClass() {

	return CcIcalcoloDcontributo.class;
    }

    @Override
    public List<CcIcalcoloDcontributo> findAll(Integer firstResult, Integer maxResult) {

	return ccicalcolodcontributoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcIcalcoloDcontributo entity) {

	if (validateEntity(entity)) {
	    ccicalcolodcontributoDAO.insert(entity);
	}
    }

    @Override
    public CcIcalcoloDcontributo findById(PkId id) {

	return ccicalcolodcontributoDAO.findById(id);
    }

    @Override
    public void update(CcIcalcoloDcontributo entity) {

	if (validateEntity(entity)) {
	    ccicalcolodcontributoDAO.update(entity);
	}
    }

    @Override
    public void delete(CcIcalcoloDcontributo entity) {

	if (isDeleteAllowed(entity)) {
	    ccicalcolodcontributoDAO.delete(entity);
	}
    }

    @Override
    public boolean existRecordByCcTipointervento(CcTipointervento entity) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", entity.getId().getCodice(), "ccTipointervento", Integer.class));
	ft.addRestriction(fr);
	int count = ccicalcolodcontributoDAO.countRecord(ft);
	if (count > 0) {
	    return true;
	} else {
	    return false;
	}
    }

    protected boolean isDeleteAllowed(CcIcalcoloDcontributo entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO _validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
