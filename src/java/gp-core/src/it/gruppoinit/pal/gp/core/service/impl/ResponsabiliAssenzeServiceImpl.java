package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabiliAssenzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliAssenze;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ResponsabiliAssenzeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class ResponsabiliAssenzeServiceImpl extends BaseServiceImpl<ResponsabiliAssenze, PkId> implements ResponsabiliAssenzeService {

    private ResponsabiliAssenzeDAO responsabiliassenzeDAO;

    @Autowired
    public void setResponsabiliAssenzeDAO(ResponsabiliAssenzeDAO responsabiliassenzeDAO) {

	this.responsabiliassenzeDAO = responsabiliassenzeDAO;
    }

    @Override
    protected Class<ResponsabiliAssenze> getEntityClass() {

	return ResponsabiliAssenze.class;
    }

    @Override
    public List<ResponsabiliAssenze> findAll(Integer firstResult, Integer maxResult) {

	return responsabiliassenzeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ResponsabiliAssenze entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertOrUpdateAllowed(entity)) {
	    responsabiliassenzeDAO.insert(entity);
	}
    }

    @Override
    public ResponsabiliAssenze findById(PkId id) {

	return responsabiliassenzeDAO.findById(id);
    }

    @Override
    public void update(ResponsabiliAssenze entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertOrUpdateAllowed(entity)) {
	    responsabiliassenzeDAO.update(entity);
	}
    }

    @Override
    public void delete(ResponsabiliAssenze entity) {

	if (isDeleteAllowed(entity)) {
	    responsabiliassenzeDAO.delete(entity);
	}
    }

    @Override
    public List<ResponsabiliAssenze> findByResponsabile(Integer codice, Integer firstResult, Integer maxResult) {

	return responsabiliassenzeDAO.findByResponsabile(codice, firstResult, maxResult);
    }

    @Override
    public boolean isAssente(Responsabili responsabili, Date date) {

	boolean ris = false;
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", responsabili.getId().getCodice(), "responsabili", Integer.class));
	fr.addFilterField(FilterUtils.greaterEqual("al", date, Date.class));
	fr.addFilterField(FilterUtils.smallerEqual("dal", date, Date.class));
	ft.addRestriction(fr);
	int count = responsabiliassenzeDAO.countRecord(ft);
	if (count > 0) {
	    return true;
	}
	return ris;
    }

    private void dataIntegration(ResponsabiliAssenze entity) {

	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(ResponsabiliAssenze entity) {

	super.fixMergeEntityProperties(entity);
    }

    private boolean isInsertOrUpdateAllowed(ResponsabiliAssenze entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getAl() != null && entity.getDal().compareTo(entity.getAl()) > 0) {
	    _ivs.add(new InvalidValue("service_error.responsabiliassenze.data_dal_maggiore_di_data_al", ResponsabiliAssenze.class, "al", entity,
		    new ResponsabiliAssenze()));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }
    //    protected boolean isDeleteAllowed(ResponsabiliAssenze entity) {
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
