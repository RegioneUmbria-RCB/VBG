package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzefidejussioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Istanzefidejussioni;
import it.gruppoinit.pal.gp.core.domain.IstanzefidejussioniId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IstanzefidejussioniService;

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
public class IstanzefidejussioniServiceImpl extends BaseServiceImpl<Istanzefidejussioni, IstanzefidejussioniId>
	implements IstanzefidejussioniService {

    private IstanzefidejussioniDAO istanzefidejussioniDAO;

    @Autowired
    public void setIstanzefidejussioniDAO(IstanzefidejussioniDAO istanzefidejussioniDAO) {

	this.istanzefidejussioniDAO = istanzefidejussioniDAO;
    }

    @Override
    protected Class<Istanzefidejussioni> getEntityClass() {

	return Istanzefidejussioni.class;
    }

    @Override
    public List<Istanzefidejussioni> findAll(Integer firstResult, Integer maxResult) {

	return istanzefidejussioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzefidejussioni entity) {

	if (validateEntity(entity)) {
	    istanzefidejussioniDAO.insert(entity);
	}
    }

    @Override
    public Istanzefidejussioni findById(IstanzefidejussioniId id) {

	return istanzefidejussioniDAO.findById(id);
    }

    @Override
    public void update(Istanzefidejussioni entity) {

	if (validateEntity(entity)) {
	    istanzefidejussioniDAO.update(entity);
	}
    }

    @Override
    public void delete(Istanzefidejussioni entity) {

	if (isDeleteAllowed(entity)) {
	    istanzefidejussioniDAO.delete(entity);
	}
    }

    @Override
    public List<Istanzefidejussioni> findByIstanze(Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanze", Integer.class));
	ft.addRestriction(fr);
	return istanzefidejussioniDAO.findByFilterTable(ft);
    }

    protected boolean isDeleteAllowed(Istanzefidejussioni entity) {

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
    public boolean existRecordByIstanza(Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanze", Integer.class));
	ft.addRestriction(fr);
	return istanzefidejussioniDAO.existsRecords(ft);
    }
}
