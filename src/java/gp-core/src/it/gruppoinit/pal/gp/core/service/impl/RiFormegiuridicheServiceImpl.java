package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.RiFormegiuridicheDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.RiFormegiuridiche;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.RiFormegiuridicheService;

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
public class RiFormegiuridicheServiceImpl extends BaseServiceImpl<RiFormegiuridiche, String> implements RiFormegiuridicheService {

    private RiFormegiuridicheDAO riformegiuridicheDAO;

    @Autowired
    public void setRiFormegiuridicheDAO(RiFormegiuridicheDAO riformegiuridicheDAO) {

	this.riformegiuridicheDAO = riformegiuridicheDAO;
    }

    @Override
    protected Class<RiFormegiuridiche> getEntityClass() {

	return RiFormegiuridiche.class;
    }

    @Override
    public List<RiFormegiuridiche> findAll(Integer firstResult, Integer maxResult) {

	return riformegiuridicheDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(RiFormegiuridiche entity) {

	if (validateEntity(entity)) {
	    riformegiuridicheDAO.insert(entity);
	}
    }

    @Override
    public RiFormegiuridiche findById(String id) {

	return riformegiuridicheDAO.findById(id);
    }

    @Override
    public void update(RiFormegiuridiche entity) {

	if (validateEntity(entity)) {
	    riformegiuridicheDAO.update(entity);
	}
    }

    @Override
    public void delete(RiFormegiuridiche entity) {

	if (isDeleteAllowed(entity)) {
	    riformegiuridicheDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(RiFormegiuridiche entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<RiFormegiuridiche> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResults) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.startsWith("descrizione", textToSearch));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return riformegiuridicheDAO.findByFilterTable(ft, firstResult, maxResults);
    }
}
