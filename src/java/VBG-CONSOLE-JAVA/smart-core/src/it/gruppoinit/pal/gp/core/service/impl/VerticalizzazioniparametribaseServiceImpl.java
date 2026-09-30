package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VerticalizzazioniparametribaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametribase;
import it.gruppoinit.pal.gp.core.domain.VerticalizzazioniparametribaseId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametribaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class VerticalizzazioniparametribaseServiceImpl extends BaseServiceImpl<Verticalizzazioniparametribase, VerticalizzazioniparametribaseId>
	implements VerticalizzazioniparametribaseService {

    private VerticalizzazioniparametribaseDAO verticalizzazioniparametribaseDAO;

    @Autowired
    public void setVerticalizzazioniparametribaseDAO(VerticalizzazioniparametribaseDAO verticalizzazioniparametribaseDAO) {

	this.verticalizzazioniparametribaseDAO = verticalizzazioniparametribaseDAO;
    }

    @Override
    protected Class<Verticalizzazioniparametribase> getEntityClass() {

	return Verticalizzazioniparametribase.class;
    }

    @Override
    public List<Verticalizzazioniparametribase> findAll(Integer firstResult, Integer maxResult) {

	return verticalizzazioniparametribaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Verticalizzazioniparametribase entity) {

	if (validateEntity(entity)) {
	    verticalizzazioniparametribaseDAO.insert(entity);
	}
    }

    @Override
    public Verticalizzazioniparametribase findById(VerticalizzazioniparametribaseId id) {

	return verticalizzazioniparametribaseDAO.findById(id);
    }

    @Override
    public void update(Verticalizzazioniparametribase entity) {

	if (validateEntity(entity)) {
	    verticalizzazioniparametribaseDAO.update(entity);
	}
    }

    @Override
    public void delete(Verticalizzazioniparametribase entity) {

	if (isDeleteAllowed(entity)) {
	    verticalizzazioniparametribaseDAO.delete(entity);
	}
    }

    // protected boolean isDeleteAllowed(Verticalizzazioniparametribase entity) {
    //
    // boolean delete = true;
    // List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    // TODO_validare_la_delete
    // // esempio:
    // // if (entity.getList().size() > 0) {
    // // _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    // // }
    // if (!_ivs.isEmpty()) {
    // this.throwValidationMessages(_ivs);
    // }
    // return delete;
    // }
    @Override
    public List<Verticalizzazioniparametribase> findByModulo(String modulo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.modulo", modulo, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.parametro"));
	return verticalizzazioniparametribaseDAO.findByFilterTable(ft);
    }
}
