package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzefrontofficeDAO;
import it.gruppoinit.pal.gp.core.domain.Istanzefrontoffice;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.IstanzefrontofficeService;

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
public class IstanzefrontofficeServiceImpl extends BaseServiceImpl<Istanzefrontoffice, PkId> implements IstanzefrontofficeService {

    private IstanzefrontofficeDAO istanzefrontofficeDAO;

    @Autowired
    public void setIstanzefrontofficeDAO(IstanzefrontofficeDAO istanzefrontofficeDAO) {

	this.istanzefrontofficeDAO = istanzefrontofficeDAO;
    }

    @Override
    protected Class<Istanzefrontoffice> getEntityClass() {

	return Istanzefrontoffice.class;
    }

    @Override
    public List<Istanzefrontoffice> findAll(Integer firstResult, Integer maxResult) {

	return istanzefrontofficeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzefrontoffice entity) {

	if (validateEntity(entity)) {
	    istanzefrontofficeDAO.insert(entity);
	}
    }

    @Override
    public Istanzefrontoffice findById(PkId id) {

	return istanzefrontofficeDAO.findById(id);
    }

    @Override
    public void update(Istanzefrontoffice entity) {

	if (validateEntity(entity)) {
	    istanzefrontofficeDAO.update(entity);
	}
    }

    @Override
    public void delete(Istanzefrontoffice entity) {

	if (isDeleteAllowed(entity)) {
	    istanzefrontofficeDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Istanzefrontoffice entity) {

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
    public List<Istanzefrontoffice> findByFilterTable(FilterTable filterTable) {

	return istanzefrontofficeDAO.findByFilterTable(filterTable);
    }
}
