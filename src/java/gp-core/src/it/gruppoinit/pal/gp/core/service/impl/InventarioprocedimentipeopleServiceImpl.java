package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentipeopleDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentipeople;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentipeopleService;

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
public class InventarioprocedimentipeopleServiceImpl extends BaseServiceImpl<Inventarioprocedimentipeople, PkId> implements
	InventarioprocedimentipeopleService {

    private InventarioprocedimentipeopleDAO inventarioprocedimentipeopleDAO;

    @Autowired
    public void setInventarioprocedimentipeopleDAO(InventarioprocedimentipeopleDAO inventarioprocedimentipeopleDAO) {

	this.inventarioprocedimentipeopleDAO = inventarioprocedimentipeopleDAO;
    }

    @Override
    protected Class<Inventarioprocedimentipeople> getEntityClass() {

	return Inventarioprocedimentipeople.class;
    }

    @Override
    public List<Inventarioprocedimentipeople> findAll(Integer firstResult, Integer maxResult) {

	return inventarioprocedimentipeopleDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Inventarioprocedimentipeople entity) {

	if (validateInsert(entity)) {
	    inventarioprocedimentipeopleDAO.insert(entity);
	}
    }

    @Override
    public Inventarioprocedimentipeople findById(PkId id) {

	return inventarioprocedimentipeopleDAO.findById(id);
    }

    @Override
    public void update(Inventarioprocedimentipeople entity) {

	if (validateUpdate(entity)) {
	    inventarioprocedimentipeopleDAO.update(entity);
	}
    }

    @Override
    public void delete(Inventarioprocedimentipeople entity) {

	if (isDeleteAllowed(entity)) {
	    inventarioprocedimentipeopleDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Inventarioprocedimentipeople entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO se utilizzato in istanzeprocedimenti di istanze create da STC
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    private boolean validateInsert(Inventarioprocedimentipeople entity) {

	boolean success = super.validateEntity(entity);
	if (success) {
	    Inventarioprocedimentipeople entitydb = this.findByCodiceSTP(entity.getCodProcPeople(), ORMHelper.getSoftware());
	    if (entitydb != null) {
		success = false;
		throwValidationMessage(new InvalidValue("Codice STP duplicato", null, null, "", null));
	    }
	}
	return success;
    }

    private boolean validateUpdate(Inventarioprocedimentipeople entity) {

	boolean success = super.validateEntity(entity);
	if (success) {
	    Inventarioprocedimentipeople entitydb = this.findByCodiceSTP(entity.getCodProcPeople(), ORMHelper.getSoftware());
	    if (entitydb != null) {
		if (!entity.getId().equals(entitydb.getId())) {
		    success = false;
		    throwValidationMessage(new InvalidValue("Codice STP duplicato", null, null, "", null));
		}
	    }
	}
	return success;
    }

    @Override
    public Inventarioprocedimentipeople findByCodiceSTP(String codiceSTP, String codiceSoftware) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.in("codice", new Object[] { WebConstants.SOFTWARE_TT, codiceSoftware },
		"inventarioprocedimenti.software", String.class));
	restriction.addFilterField(FilterUtils.equals("codProcPeople", codiceSTP, String.class));
	filterTable.addRestriction(restriction);
	List<Inventarioprocedimentipeople> list = inventarioprocedimentipeopleDAO.findByFilterTable(filterTable);
	return list.isEmpty() ? null : list.get(0);
    }
}
