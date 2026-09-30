package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeoneriDettaglioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.IstanzeoneriDettaglio;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IstanzeoneriDettaglioService;

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
public class IstanzeoneriDettaglioServiceImpl extends BaseServiceImpl<IstanzeoneriDettaglio, PkId> implements IstanzeoneriDettaglioService {

    private IstanzeoneriDettaglioDAO istanzeoneridettaglioDAO;

    @Autowired
    public void setIstanzeoneriDettaglioDAO(IstanzeoneriDettaglioDAO istanzeoneridettaglioDAO) {

	this.istanzeoneridettaglioDAO = istanzeoneridettaglioDAO;
    }

    @Override
    protected Class<IstanzeoneriDettaglio> getEntityClass() {

	return IstanzeoneriDettaglio.class;
    }

    @Override
    public List<IstanzeoneriDettaglio> findAll(Integer firstResult, Integer maxResult) {

	return istanzeoneridettaglioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IstanzeoneriDettaglio entity) {

	if (validateEntity(entity)) {
	    istanzeoneridettaglioDAO.insert(entity);
	}
    }

    @Override
    public IstanzeoneriDettaglio findById(PkId id) {

	return istanzeoneridettaglioDAO.findById(id);
    }

    @Override
    public void update(IstanzeoneriDettaglio entity) {

	if (validateEntity(entity)) {
	    istanzeoneridettaglioDAO.update(entity);
	}
    }

    @Override
    public void delete(IstanzeoneriDettaglio entity) {

	if (isDeleteAllowed(entity)) {
	    istanzeoneridettaglioDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(IstanzeoneriDettaglio entity) {

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
    public int countByInventarioprocedimento(Integer codiceProcedimento) {

	if (codiceProcedimento == null) {
	    throw new IllegalArgumentException("countByInventarioprocedimento: il parametro codiceProcedimento e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceProcedimento, "inventarioprocedimenti", Integer.class));
	filterTable.addRestriction(fr);
	int count = istanzeoneridettaglioDAO.countRecord(filterTable);
	return count;
    }
}
