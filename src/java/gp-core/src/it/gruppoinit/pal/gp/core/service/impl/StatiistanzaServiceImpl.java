package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.StatiistanzaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Staticomportamento;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StaticomportamentoService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;

@Service
public class StatiistanzaServiceImpl extends BaseServiceImpl<Statiistanza, StatiistanzaId> implements StatiistanzaService {

    private SoftwareService softwareService;
    private StaticomportamentoService staticomportamentoService;
    private StatiistanzaDAO statiistanzaDAO;

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setStaticomportamentoService(StaticomportamentoService staticomportamentoService) {

	this.staticomportamentoService = staticomportamentoService;
    }

    @Autowired
    public void setStatiistanzaDAO(StatiistanzaDAO statiistanzaDAO) {

	this.statiistanzaDAO = statiistanzaDAO;
    }

    @Override
    protected Class<Statiistanza> getEntityClass() {

	return Statiistanza.class;
    }

    @Override
    public void delete(Statiistanza entity) {

	statiistanzaDAO.delete(entity);
    }

    @Override
    public List<Statiistanza> findAll(Integer firstResult, Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	filterTable.addOrder(FilterUtils.order("ordine", OrderTypeEnum.ASC));
	filterTable.addOrder(FilterUtils.order("stato", OrderTypeEnum.ASC));
	return statiistanzaDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public Statiistanza findById(StatiistanzaId id) {

	return statiistanzaDAO.findById(id);
    }

    @Override
    public void insert(Statiistanza entity) {

	dataIntegration(entity);
	if (validateEntity(entity, true)) {
	    statiistanzaDAO.insert(entity);
	}
    }

    @Override
    public void update(Statiistanza entity) {

	dataIntegration(entity);
	if (validateEntity(entity, false)) {
	    statiistanzaDAO.update(entity);
	}
    }

    private void dataIntegration(Statiistanza entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Stato istanza nullo");
	}
	Staticomportamento comportamento = staticomportamentoService.bindDomainObject(entity.getStaticomportamento(), Integer.class,
		"codcomportamento");
	entity.setStaticomportamento(comportamento);
	if (entity.getSoftware() == null) {
	    Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	    entity.setSoftware(software);
	}
	if (entity.getFlagBloccaIntegrOnline() == null) {
	    entity.setFlagBloccaIntegrOnline(false);
	}
    }

    protected boolean validateEntity(Statiistanza entity, boolean isInsert) {

	super.validateEntity(entity);
	if (isInsert) {
	    String codicestato = entity.getId().getCodicestato();
	    StatiistanzaId id = new StatiistanzaId();
	    id.setSoftware(entity.getId().getSoftware());
	    id.setIdcomune(entity.getId().getIdcomune());
	    id.setCodicestato(codicestato);
	    Statiistanza statiistanza = this.findById(id);
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    if (statiistanza != null) {
		_ivs.add(new InvalidValue("statiistanze.error.codicestato_presente", entity.getClass(), "", "", entity));
		this.throwValidationMessages(_ivs);
	    }
	}
	return true;
    }

    @Override
    public List<Statiistanza> findBySoftware(String software) {

	return statiistanzaDAO.findBySoftware(software);
    }

    protected boolean isDeleteAllowed(Statiistanza entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	boolean isUsatoInIstanze = this.isUsedByIstanze(entity);
	if (isUsatoInIstanze) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ISTANZE", null));
	    delete = false;
	}
	boolean isUsatoInTipiMov = this.isUsedByTipimovimento(entity);
	if (isUsatoInTipiMov) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "TIPIMOVIMENTOS", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Statiistanza> findByStatocomportamentoChiuse() {

	return statiistanzaDAO.findByStatocomportamentoChiuse();
    }

    @Override
    public List<Statiistanza> findByStatocomportamentoAperte() {

	return statiistanzaDAO.findByStatocomportamentoAperte();
    }

    @Override
    public List<Statiistanza> findByFilterTable(FilterTable filterTable) {

	return statiistanzaDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Statiistanza> findByStatocomportamentoChiuse(boolean tuttiSoftware) {

	return statiistanzaDAO.findByStatocomportamentoChiuse(tuttiSoftware);
    }

    @Override
    public List<Statiistanza> findByStatocomportamentoAperte(boolean tuttiSoftware) {

	return statiistanzaDAO.findByStatocomportamentoAperte(tuttiSoftware);
    }

    @Override
    public boolean isUsedByTipimovimento(Statiistanza statiistanza) {

	return isUsedBy(statiistanza, "tipimovimentos");
    }

    @Override
    public boolean isUsedByIstanze(Statiistanza statiistanza) {

	return isUsedBy(statiistanza, "tipimovimentos");
    }

    private boolean isUsedBy(Statiistanza statiistanza, String collectionProperty) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction basefilter = new FilterRestriction();
	basefilter.addFilterField(FilterUtils.equals("id.codicestato", statiistanza.getId().getCodicestato(), String.class));
	basefilter.addFilterField(FilterUtils.isNotEmpty(collectionProperty));
	filterTable.addRestriction(basefilter);
	int result = statiistanzaDAO.countRecord(filterTable);
	return result > 0;
    }

    @Override
    public List<Statiistanza> findStati(String software) {

	return statiistanzaDAO.findStati(software);
    }
}
