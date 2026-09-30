package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ProtocolloTipidocumentoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloTipidocumento;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ProtocolloTipidocumentoService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class ProtocolloTipidocumentoServiceImpl extends BaseServiceImpl<ProtocolloTipidocumento, PkId> implements ProtocolloTipidocumentoService {

    private ProtocolloTipidocumentoDAO protocollotipidocumentoDAO;
    private ComuniService comuniService;
    private SoftwareService softwareService;

    @Autowired
    public void setProtocolloTipidocumentoDAO(ProtocolloTipidocumentoDAO protocollotipidocumentoDAO) {

	this.protocollotipidocumentoDAO = protocollotipidocumentoDAO;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Override
    protected Class<ProtocolloTipidocumento> getEntityClass() {

	return ProtocolloTipidocumento.class;
    }

    @Override
    public List<ProtocolloTipidocumento> findAll(Integer firstResult, Integer maxResult) {

	return protocollotipidocumentoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ProtocolloTipidocumento entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    protocollotipidocumentoDAO.insert(entity);
	}
    }

    private boolean isInsertAllowed(ProtocolloTipidocumento entity) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equalsIgnoreCase("codice", entity.getCodice()));
	String codiceComune = null;
	if (entity.getComune() != null) {
	    if (StringUtils.isNotBlank(entity.getComune().getCodicecomune())) {
		codiceComune = entity.getComune().getCodicecomune();
	    }
	}
	if (codiceComune == null) {
	    filterRestriction.addFilterField(FilterUtils.isNull("codicecomune", "comune"));
	} else {
	    filterRestriction.addFilterField(FilterUtils.equalsIgnoreCase("codicecomune", codiceComune, "comune"));
	}
	filterRestriction.addFilterField(FilterUtils.equalsIgnoreCase("codice", entity.getSoftware().getCodice(), "software"));
	filterTable.addRestriction(filterRestriction);
	int c = protocollotipidocumentoDAO.countRecord(filterTable);
	if (c > 0) {
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    _ivs.add(new InvalidValue("service_error.protocollo_tipidocumento.configurazione_presente_per_codice", null, "codice", entity.getCodice(), null));
	    if (!_ivs.isEmpty()) {
		this.throwValidationMessages(_ivs);
	    }
	}
	return true;
    }

    @Override
    public ProtocolloTipidocumento findById(PkId id) {

	return protocollotipidocumentoDAO.findById(id);
    }

    @Override
    public void update(ProtocolloTipidocumento entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    protocollotipidocumentoDAO.update(entity);
	}
    }

    @Override
    public void delete(ProtocolloTipidocumento entity) {

	if (isDeleteAllowed(entity)) {
	    protocollotipidocumentoDAO.delete(entity);
	}
    }

    @Override
    public List<ProtocolloTipidocumento> findByDescrizione(String textToSearch) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.like("descrizione", textToSearch));
	filterTable.addRestriction(filterRestriction);
	filterTable.addOrder(FilterUtils.orderAsc("descrizione"));
	return protocollotipidocumentoDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<ProtocolloTipidocumento> findAllOrederByComuneAndSoftware(Integer firstResult, Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterTable.addRestriction(filterRestriction);
	filterTable.addOrder(FilterUtils.orderAsc("codicecomune", "comune"));
	filterTable.addOrder(FilterUtils.orderAsc("codice", "software"));
	filterTable.addOrder(FilterUtils.orderAsc("descrizione"));
	if (null != firstResult && null != maxResult) {
	    return protocollotipidocumentoDAO.findByFilterTable(filterTable, firstResult, maxResult);
	} else {
	    return protocollotipidocumentoDAO.findByFilterTable(filterTable);
	}
    }

    private void dataIntegration(ProtocolloTipidocumento entity, boolean isInsert) {

	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(ProtocolloTipidocumento entity) {

	Comuni comune = comuniService.bindDomainObject(entity.getComune(), String.class, "codicecomune");
	entity.setComune(comune);
	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
    }

    protected boolean isDeleteAllowed(ProtocolloTipidocumento entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO la validazione va fatta tenendo conto che il valore è configurato in tipimov stc mapping 
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
