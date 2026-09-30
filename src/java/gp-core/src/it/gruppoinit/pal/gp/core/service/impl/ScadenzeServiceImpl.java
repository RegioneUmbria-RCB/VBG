package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ScadenzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ScadenzecategoriebaseEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Scadenze;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.ScadenzeService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class ScadenzeServiceImpl extends BaseServiceImpl<Scadenze, PkId> implements ScadenzeService {

    private ScadenzeDAO scadenzeDAO;

    @Autowired
    public void setScadenzeDAO(ScadenzeDAO scadenzeDAO) {

	this.scadenzeDAO = scadenzeDAO;
    }

    @Override
    protected Class<Scadenze> getEntityClass() {

	return Scadenze.class;
    }

    @Override
    public List<Scadenze> findAll(Integer firstResult, Integer maxResult) {

	return scadenzeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Scadenze entity) {

	if (validateEntity(entity)) {
	    scadenzeDAO.insert(entity);
	}
    }

    @Override
    public Scadenze findById(PkId id) {

	return scadenzeDAO.findById(id);
    }

    @Override
    public void update(Scadenze entity) {

	if (validateEntity(entity)) {
	    scadenzeDAO.update(entity);
	}
    }

    @Override
    public void delete(Scadenze entity) {

	if (isDeleteAllowed(entity)) {
	    scadenzeDAO.delete(entity);
	}
    }

    // protected boolean isDeleteAllowed(Scadenze entity) {
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
    public List<Scadenze> findAvvisiForAnagrafe(Anagrafe anagrafe, boolean visualizzaSoloAttive) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (EntityUtils.getNestedProperty(anagrafe, "id.codice") != null) {
	    fr.addFilterField(FilterUtils.equals("anagrafeId", anagrafe.getId().getCodice(), Integer.class));
	}
	Date oggi = Calendar.getInstance().getTime();
	FilterField<Date> dataRegistrazione = new FilterField<Date>("dataregistrazione", FieldOperationsEnum.LE, new Date[] { oggi }, Date.class);
	fr.addFilterField(dataRegistrazione);
	FilterField<Date> datascadenza = new FilterField<Date>("datascadenza", FieldOperationsEnum.GE, new Date[] { oggi }, Date.class);
	fr.addFilterField(datascadenza);
	fr.addFilterField(FilterUtils.equals("flagNascondi", Boolean.FALSE, Boolean.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("categoria"));
	ft.addOrder(FilterUtils.orderAsc("datascadenza"));
	return scadenzeDAO.findByFilterTable(ft);
    }

    @Override
    public List<Scadenze> findAvvisiForAnagrafe(Integer codiceAnagrafe, ScadenzecategoriebaseEnum scadenzecategoriebaseEnum) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (codiceAnagrafe != null) {
	    fr.addFilterField(FilterUtils.equals("anagrafeId", codiceAnagrafe, Integer.class));
	}
	switch (scadenzecategoriebaseEnum) {
	case INTERDIZIONE:
	    fr.addFilterField(FilterUtils.equals("categoria", WebConstants.INTERDETTI, String.class));
	    break;
	case SCADENZA:
	    fr.addFilterField(FilterUtils.equals("categoria", WebConstants.SCADENZA, String.class));
	    break;
	case AVVIVO:
	    fr.addFilterField(FilterUtils.equals("categoria", WebConstants.AVVISO, String.class));
	    break;
	case ALL:
	    break;
	default:
	    throw new RuntimeException("wrong switch value!");
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.order("datascadenza", OrderTypeEnum.DESC));
	List<Scadenze> list = scadenzeDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public boolean findExistsAvvisiForAnagrafe(Integer codiceAnagrafe, boolean visualizzaSoloAttive) {

	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("Il parametro codiceAnagrafe non può essere nullo per la funzione findExistsAvvisiForAnagrafe");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("anagrafeId", codiceAnagrafe, Integer.class));
	Date oggi = Calendar.getInstance().getTime();
	FilterField<Date> dataRegistrazione = new FilterField<Date>("dataregistrazione", FieldOperationsEnum.LE, new Date[] { oggi }, Date.class);
	fr.addFilterField(dataRegistrazione);
	FilterField<Date> datascadenza = new FilterField<Date>("datascadenza", FieldOperationsEnum.GE, new Date[] { oggi }, Date.class);
	fr.addFilterField(datascadenza);
	fr.addFilterField(FilterUtils.equals("flagNascondi", Boolean.FALSE, Boolean.class));
	ft.addRestriction(fr);
	int counts = scadenzeDAO.countRecord(ft);
	return counts > 0;
    }
}
