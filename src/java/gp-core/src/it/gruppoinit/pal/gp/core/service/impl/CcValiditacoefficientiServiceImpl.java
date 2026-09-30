package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.CcValiditacoefficientiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CcCoeffcontribAttivita;
import it.gruppoinit.pal.gp.core.domain.CcCoeffcontributo;
import it.gruppoinit.pal.gp.core.domain.CcValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CcCoeffcontribAttivitaService;
import it.gruppoinit.pal.gp.core.service.CcCoeffcontributoService;
import it.gruppoinit.pal.gp.core.service.CcIcalcolototService;
import it.gruppoinit.pal.gp.core.service.CcValiditacoefficientiService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class CcValiditacoefficientiServiceImpl extends BaseServiceImpl<CcValiditacoefficienti, PkId> implements CcValiditacoefficientiService {

    private CcCoeffcontribAttivitaService ccCoeffcontribAttivitaService;
    private CcCoeffcontributoService ccoeffcontributoService;
    private CcIcalcolototService ccIcalcolototService;
    private CcValiditacoefficientiDAO ccvaliditacoefficientiDAO;

    @Autowired
    public void setCcCoeffcontribAttivitaService(CcCoeffcontribAttivitaService ccCoeffcontribAttivitaService) {

	this.ccCoeffcontribAttivitaService = ccCoeffcontribAttivitaService;
    }

    @Autowired
    public void setCcoeffcontributoService(CcCoeffcontributoService ccoeffcontributoService) {

	this.ccoeffcontributoService = ccoeffcontributoService;
    }

    @Autowired
    public void setCcIcalcolototService(CcIcalcolototService ccIcalcolototService) {

	this.ccIcalcolototService = ccIcalcolototService;
    }

    @Autowired
    public void setCcValiditacoefficientiDAO(CcValiditacoefficientiDAO ccvaliditacoefficientiDAO) {

	this.ccvaliditacoefficientiDAO = ccvaliditacoefficientiDAO;
    }

    @Override
    protected Class<CcValiditacoefficienti> getEntityClass() {

	return CcValiditacoefficienti.class;
    }

    @Override
    public List<CcValiditacoefficienti> findAll(Integer firstResult, Integer maxResult) {

	return ccvaliditacoefficientiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcValiditacoefficienti entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity, true)) {
	    ccvaliditacoefficientiDAO.insert(entity);
	}
    }

    private void dataIntegration(CcValiditacoefficienti entity) {

	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(CcValiditacoefficienti entity) {

    }

    @Override
    public CcValiditacoefficienti findById(PkId id) {

	return ccvaliditacoefficientiDAO.findById(id);
    }

    @Override
    public void update(CcValiditacoefficienti entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity, false)) {
	    ccvaliditacoefficientiDAO.update(entity);
	}
    }

    @Override
    public void delete(CcValiditacoefficienti entity) {

	if (isDeleteAllowed(entity)) {
	    ccvaliditacoefficientiDAO.delete(entity);
	}
    }

    @Override
    public CcValiditacoefficienti findByEqualsDescrizione(String descrizione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("descrizione", descrizione, String.class));
	ft.addRestriction(fr);
	List<CcValiditacoefficienti> list = ccvaliditacoefficientiDAO.findByFilterTable(ft);
	if (list != null && !list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public List<CcValiditacoefficienti> findByFilterTable(FilterTable filterTable) {

	return ccvaliditacoefficientiDAO.findByFilterTable(filterTable);
    }

    protected void childDelete(CcValiditacoefficienti entity) {

	Set<CcCoeffcontribAttivita> ccCoeffcontribAttivitas = entity.getCcCoeffcontribAttivitas();
	for (CcCoeffcontribAttivita ccCoeffcontribAttivita : ccCoeffcontribAttivitas) {
	    ccCoeffcontribAttivitaService.delete(ccCoeffcontribAttivita);
	}
	Set<CcCoeffcontributo> ccCoeffcontributos = entity.getCcCoeffcontributos();
	for (CcCoeffcontributo ccCoeffcontributo : ccCoeffcontributos) {
	    ccoeffcontributoService.delete(ccCoeffcontributo);
	}
    }

    protected boolean isInsertAllowed(CcValiditacoefficienti entity, boolean isInsert) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	CcValiditacoefficienti CcValiditacoefficienti = this.findByEqualsDescrizione(entity.getDescrizione());
	// TODO _validare_la_delete
	// esempio:
	if (isInsert) {
	    if (CcValiditacoefficienti != null && EntityUtils.getNestedProperty(entity, "id.codice") == null) {
		_ivs.add(new InvalidValue("service_error.descrizione_coefficienti_presente", null, null, null, null));
	    }
	} else {
	    if (CcValiditacoefficienti != null && EntityUtils.getNestedProperty(entity, "id.codice") != null
		    && !entity.getId().getCodice().equals(CcValiditacoefficienti.getId().getCodice())) {
		_ivs.add(new InvalidValue("service_error.descrizione_coefficienti_presente", null, null, null, null));
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    protected boolean isDeleteAllowed(CcValiditacoefficienti entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	int numeroCcIcalcolotot = ccIcalcolototService.countByCcValiditacoefficienti(entity);
	if (numeroCcIcalcolotot > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "CC_ICALCOLOT", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CcValiditacoefficientiService#listByDataValidita()
     */
    @Override
    public List<CcValiditacoefficienti> listByDataValidita() {

	return ccvaliditacoefficientiDAO.listByDataValidita();
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CcValiditacoefficientiService#findValidoAllaData(java.util.Date)
     */
    @Override
    public CcValiditacoefficienti findValidoAllaData(Date validoAllaData) {

	return ccvaliditacoefficientiDAO.findValidoAllaData(validoAllaData);
    }

    @Override
    public boolean existRecordByCurrentSoftware() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	return ccvaliditacoefficientiDAO.existsRecords(ft);
    }
}
