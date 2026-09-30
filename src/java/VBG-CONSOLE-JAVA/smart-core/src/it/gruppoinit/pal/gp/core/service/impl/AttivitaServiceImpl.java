/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AttivitaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.SettoriId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.SettoriService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
@Service
public class AttivitaServiceImpl extends BaseServiceImpl<Attivita, AttivitaId> implements AttivitaService {

    private AttivitaDAO attivitaDAO;
    private SoftwareService softwareService;
    private ConfigurazioneService configurazioneService;
    private SettoriService settoriService;

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setAttivitaDAO(AttivitaDAO attivitaDAO) {

	this.attivitaDAO = attivitaDAO;
    }

    @Autowired
    public void setSettoriService(SettoriService settoriService) {

	this.settoriService = settoriService;
    }

    @Override
    protected Class<Attivita> getEntityClass() {

	return Attivita.class;
    }

    @Override
    public void delete(Attivita entity) {

	if (isDeleteAllowed(entity)) {
	    attivitaDAO.delete(entity);
	    resetObjectCached();
	}
    }

    @Override
    public List<Attivita> findAll(Integer firstResult, Integer maxResult) {

	return attivitaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Attivita findById(AttivitaId id) {

	return attivitaDAO.findById(id);
    }

    @Override
    public void insert(Attivita entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    // forzo l'inserimento del codice maiuscolo
	    entity.getId().setCodiceistat(entity.getId().getCodiceistat().toUpperCase());
	    attivitaDAO.insert(entity);
	    resetObjectCached();
	}
    }

    @Override
    public void update(Attivita entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    attivitaDAO.update(entity);
	    resetObjectCached();
	}
    }

    /**
     * è stato necessario utilizzare tale metodo per determinare le attività di un settore. é necessario poichè la
     * proprietà del dominio Settori ha un set delle attività, invece il resto dell'applicazione utilizza le liste. in
     * questo modo determino direttamente una lista.
     */
    @Override
    public List<Attivita> findAttivitaBySettore(String codicesettore, String orderByProperty) {

	if (StringUtils.isBlank(codicesettore)) {
	    throw new RuntimeException("getAttivitaBySettore: CodiceSettore non può essere vuoto");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codicesettore", codicesettore, "settori", String.class));
	fr.addFilterField(FilterUtils.equals("flagDisabilitato", false, Boolean.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc(orderByProperty));
	List<Attivita> listAttivitaSettore = attivitaDAO.findByFilterTable(ft);
	return listAttivitaSettore;
    }

    public List<Attivita> findByFilter(Attivita attivita) {

	return attivitaDAO.findByFilter(attivita);
    }

    protected boolean isDeleteAllowed(Attivita entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!entity.getInsediamentis().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "INSEDIAMENTI", null));
	}
	if (!entity.getMercatiCfgAttivitas().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATI_CFG_ATTIVITA", null));
	}
	if (!entity.getMercatiDattivitaistats().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATID_ATTIVITAISTAT", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    protected boolean isInsertAllowed(Attivita entity) {

	boolean insert = true;
	// forzo il codice a maiuscolo la ricerca deve essere caseInsenitive
	entity.getId().setCodiceistat(entity.getId().getCodiceistat().toUpperCase());
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Attivita objectDB = null;
	objectDB = attivitaDAO.findById(entity.getId());
	if (objectDB != null && !objectDB.getId().getCodiceistat().equals("")) {
	    _ivs.add(new InvalidValue("service_error.duplicate_codice", null, null, entity.getId().getCodiceistat(), null));
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	this.isRecordPresentiMap = new HashMap<String, Boolean>();
    }

    private Map<String, Boolean> isRecordPresentiMap = new HashMap<String, Boolean>();

    private String getMapKey(String idcomunealias, String software) {

	return idcomunealias + "-" + software;
    }

    @Override
    public boolean existsRecords() {

	if (isRecordPresentiMap == null) {
	    isRecordPresentiMap = new HashMap<String, Boolean>();
	}
	String key = getMapKey(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware());
	if (isRecordPresentiMap.get(key) != null) {
	    return isRecordPresentiMap.get(key);
	} else {
	    boolean exists = attivitaDAO.existsRecords(new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE));
	    if (!exists) {
		ConfigurazioneId id = new ConfigurazioneId();
		Configurazione conf = configurazioneService.findById(id);
		if (conf != null) {
		    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		    FilterRestriction fr = new FilterRestriction();
		    fr.addFilterField(FilterUtils.equals("codice", WebConstants.SOFTWARE_TT, "software", String.class));
		    ft.addRestriction(fr);
		    exists = attivitaDAO.existsRecords(ft);
		}
	    }
	    isRecordPresentiMap.put(key, Boolean.valueOf(exists));
	    return exists;
	}
    }

    private void dataIntegration(Attivita entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("l'Attività passata è nullo");
	}
	fixMergeEntityProperties(entity);
	if (entity.getFlagDisabilitato() == null) {
	    entity.setFlagDisabilitato(Boolean.FALSE);
	}
    }

    protected void fixMergeEntityProperties(Attivita entity) {

	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
	Settori settore = settoriService.bindDomainObject(entity.getSettori(), SettoriId.class, "id.codicesettore");
	entity.setSettori(settore);
    }
}
