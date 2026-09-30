package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MovimentiAttiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.MovimentiAtti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MovimentiAttiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class MovimentiAttiServiceImpl extends BaseServiceImpl<MovimentiAtti, PkId> implements MovimentiAttiService {

    private MovimentiAttiDAO movimentiattiDAO;

    @Autowired
    public void setMovimentiAttiDAO(MovimentiAttiDAO movimentiattiDAO) {

	this.movimentiattiDAO = movimentiattiDAO;
    }

    @Override
    protected Class<MovimentiAtti> getEntityClass() {

	return MovimentiAtti.class;
    }

    @Override
    public List<MovimentiAtti> findAll(Integer firstResult, Integer maxResult) {

	return movimentiattiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(MovimentiAtti entity) {

	if (validateEntity(entity)) {
	    movimentiattiDAO.insert(entity);
	}
    }

    @Override
    public MovimentiAtti findById(PkId id) {

	return movimentiattiDAO.findById(id);
    }

    @Override
    public void update(MovimentiAtti entity) {

	if (validateEntity(entity)) {
	    movimentiattiDAO.update(entity);
	}
    }

    @Override
    public void delete(MovimentiAtti entity) {

	if (isDeleteAllowed(entity)) {
	    movimentiattiDAO.delete(entity);
	}
    }

    @Override
    public MovimentiAtti findByMovimento(Integer codiceMovimento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.codice", codiceMovimento, "movimenti", Integer.class));
	ft.addRestriction(filterRestriction);
	List<MovimentiAtti> attis = movimentiattiDAO.findByFilterTable(ft);
	MovimentiAtti movimentiAtti = null;
	if (!attis.isEmpty()) {
	    movimentiAtti = attis.get(0);
	}
	return movimentiAtti;
    }
    //    protected boolean isDeleteAllowed(MovimentiAtti entity) {
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
}
