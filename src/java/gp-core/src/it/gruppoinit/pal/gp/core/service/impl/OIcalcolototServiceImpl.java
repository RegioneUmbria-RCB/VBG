package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OIcalcolototDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.OIcalcolotot;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.OIcalcolototService;

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
public class OIcalcolototServiceImpl extends BaseServiceImpl<OIcalcolotot, PkId> implements OIcalcolototService {

    private OIcalcolototDAO oicalcolototDAO;

    @Autowired
    public void setOIcalcolototDAO(OIcalcolototDAO oicalcolototDAO) {

	this.oicalcolototDAO = oicalcolototDAO;
    }

    @Override
    protected Class<OIcalcolotot> getEntityClass() {

	return OIcalcolotot.class;
    }

    @Override
    public List<OIcalcolotot> findAll(Integer firstResult, Integer maxResult) {

	return oicalcolototDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OIcalcolotot entity) {

	if (validateEntity(entity)) {
	    oicalcolototDAO.insert(entity);
	}
    }

    @Override
    public OIcalcolotot findById(PkId id) {

	return oicalcolototDAO.findById(id);
    }

    @Override
    public void update(OIcalcolotot entity) {

	if (validateEntity(entity)) {
	    oicalcolototDAO.update(entity);
	}
    }

    @Override
    public void delete(OIcalcolotot entity) {

	if (isDeleteAllowed(entity)) {
	    oicalcolototDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OIcalcolotot entity) {

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

    @Override
    public List<OIcalcolotot> findByIstanza(Integer codiceistanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceistanza, "istanze", Integer.class));
	ft.addRestriction(fr);
	return oicalcolototDAO.findByFilterTable(ft);
    }
}
