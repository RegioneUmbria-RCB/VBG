package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcCoeffcontributoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.CcCoeffcontributo;
import it.gruppoinit.pal.gp.core.domain.CcCondizioniAttivita;
import it.gruppoinit.pal.gp.core.domain.CcDestinazioni;
import it.gruppoinit.pal.gp.core.domain.CcTipointervento;
import it.gruppoinit.pal.gp.core.domain.CcValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AreeService;
import it.gruppoinit.pal.gp.core.service.CcCoeffcontributoService;
import it.gruppoinit.pal.gp.core.service.CcCondizioniAttivitaService;
import it.gruppoinit.pal.gp.core.service.CcDestinazioniService;
import it.gruppoinit.pal.gp.core.service.CcTipointerventoService;
import it.gruppoinit.pal.gp.core.service.CcValiditacoefficientiService;

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
public class CcCoeffcontributoServiceImpl extends BaseServiceImpl<CcCoeffcontributo, PkId> implements CcCoeffcontributoService {

    private AreeService areeService;

    @Autowired
    public void setAreeService(AreeService areeService) {

	this.areeService = areeService;
    }

    private CcCoeffcontributoDAO cccoeffcontributoDAO;

    @Autowired
    public void setCcCoeffcontributoDAO(CcCoeffcontributoDAO cccoeffcontributoDAO) {

	this.cccoeffcontributoDAO = cccoeffcontributoDAO;
    }

    private CcCondizioniAttivitaService ccCondizioniAttivitaService;

    @Autowired
    public void setCcCondizioniAttivitaService(CcCondizioniAttivitaService ccCondizioniAttivitaService) {

	this.ccCondizioniAttivitaService = ccCondizioniAttivitaService;
    }

    private CcDestinazioniService ccDestinazioniService;

    @Autowired
    public void setCcDestinazioniService(CcDestinazioniService ccDestinazioniService) {

	this.ccDestinazioniService = ccDestinazioniService;
    }

    private CcValiditacoefficientiService ccValiditacoefficientiService;

    @Autowired
    public void setCcValiditacoefficientiService(CcValiditacoefficientiService ccValiditacoefficientiService) {

	this.ccValiditacoefficientiService = ccValiditacoefficientiService;
    }

    private CcTipointerventoService ccTipointerventoService;

    @Autowired
    public void setCcTipointerventoService(CcTipointerventoService ccTipointerventoService) {

	this.ccTipointerventoService = ccTipointerventoService;
    }

    @Override
    protected Class<CcCoeffcontributo> getEntityClass() {

	return CcCoeffcontributo.class;
    }

    @Override
    public List<CcCoeffcontributo> findAll(Integer firstResult, Integer maxResult) {

	return cccoeffcontributoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcCoeffcontributo entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity, true)) {
	    cccoeffcontributoDAO.insert(entity);
	}
    }

    @Override
    public CcCoeffcontributo findById(PkId id) {

	return cccoeffcontributoDAO.findById(id);
    }

    @Override
    public void update(CcCoeffcontributo entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity, false)) {
	    cccoeffcontributoDAO.update(entity);
	}
    }

    @Override
    public void delete(CcCoeffcontributo entity) {

	if (isDeleteAllowed(entity)) {
	    cccoeffcontributoDAO.delete(entity);
	}
    }

    @Override
    public boolean existRecordByCcTipointervento(CcTipointervento entity) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", entity.getId().getCodice(), "ccTipointervento", Integer.class));
	ft.addRestriction(fr);
	int count = cccoeffcontributoDAO.countRecord(ft);
	if (count > 0) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public List<CcCoeffcontributo> findByValiditaCoefficiente(CcValiditacoefficienti ccValiditacoefficienti) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", ccValiditacoefficienti.getId().getCodice(), "ccValiditacoefficienti", Integer.class));
	ft.addRestriction(fr);
	return cccoeffcontributoDAO.findByFilterTable(ft, null, null);
    }

    @Override
    public CcCoeffcontributo findByCoefficienteAndccDestinazioniAndccTipointervento(CcValiditacoefficienti validitacoefficienti ,CcDestinazioni ccDestinazioni, CcTipointervento ccTipointervento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", ccDestinazioni.getId().getCodice(), "ccDestinazioni", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", ccTipointervento.getId().getCodice(), "ccTipointervento", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", validitacoefficienti.getId().getCodice(), "ccValiditacoefficienti", Integer.class));
	ft.addRestriction(fr);
	List<CcCoeffcontributo> ccCoeffcontributos = cccoeffcontributoDAO.findByFilterTable(ft);
	if (!ccCoeffcontributos.isEmpty()) {
	    return ccCoeffcontributos.get(0);
	}
	return null;
    }

    private void dataIntegration(CcCoeffcontributo entity) {

	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(CcCoeffcontributo entity) {

	CcDestinazioni ccDestinazioni = ccDestinazioniService.bindDomainObject(entity.getCcDestinazioni(), PkId.class, "id.codice");
	entity.setCcDestinazioni(ccDestinazioni);
	Aree aree = areeService.bindDomainObject(entity.getAree(), PkId.class, "id.codice");
	entity.setAree(aree);
	CcCondizioniAttivita ccCondizioniAttivita = ccCondizioniAttivitaService.bindDomainObject(entity.getCcCondizioniAttivita(), PkId.class,
		"id.codice");
	entity.setCcCondizioniAttivita(ccCondizioniAttivita);
	CcValiditacoefficienti ccValiditacoefficienti = ccValiditacoefficientiService.bindDomainObject(entity.getCcValiditacoefficienti(),
		PkId.class, "id.codice");
	entity.setCcValiditacoefficienti(ccValiditacoefficienti);
	CcTipointervento ccTipointervento = ccTipointerventoService.bindDomainObject(entity.getCcTipointervento(), PkId.class, "id.codice");
	entity.setCcTipointervento(ccTipointervento);
    }

    protected boolean isInsertAllowed(CcCoeffcontributo entity, boolean isInsert) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	CcCoeffcontributo ccCoeffcontributo = this.findByCoefficienteAndccDestinazioniAndccTipointervento(entity.getCcValiditacoefficienti(),entity.getCcDestinazioni(), entity.getCcTipointervento());
	if (isInsert) {
	    if (ccCoeffcontributo != null && EntityUtils.getNestedProperty(entity, "id.codice") == null) {
		_ivs.add(new InvalidValue("service_error.combinazione_destinazione_intervento_esistente", null, null, null, null));
	    }
	} else {
	    if (ccCoeffcontributo != null && EntityUtils.getNestedProperty(entity, "id.codice") != null
		    && !entity.getId().getCodice().equals(ccCoeffcontributo.getId().getCodice())) {
		_ivs.add(new InvalidValue("service_error.combinazione_destinazione_intervento_esistente", null, null, null, null));
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    protected boolean isDeleteAllowed(CcCoeffcontributo entity) {

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
}
