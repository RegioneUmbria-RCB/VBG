/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.SettoriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.SettoriId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipiunitamisura;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.SettoriService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiunitamisuraService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
@Service
public class SettoriServiceImpl extends BaseServiceImpl<Settori, SettoriId> implements SettoriService {

    /**
     * Bug Spring: il nome della variabile non può iniziare con "set". sostituito nome variabile da settoriDAO a
     * sectorsDAO
     */
    private SettoriDAO sectorsDAO;
    private SoftwareService softwareService;
    private TipiunitamisuraService tipiunitamisuraService;
    private AttivitaService attivitaService;

    @Autowired
    public void setAttivitaService(AttivitaService attivitaService) {

	this.attivitaService = attivitaService;
    }

    /**
     * @param sectorsDAO
     *            the sectorsDAO to set
     */
    @Autowired
    public void setSectorsDAO(SettoriDAO sectorsDAO) {

	this.sectorsDAO = sectorsDAO;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setTipiunitamisuraService(TipiunitamisuraService tipiunitamisuraService) {

	this.tipiunitamisuraService = tipiunitamisuraService;
    }

    @Override
    protected Class<Settori> getEntityClass() {

	return Settori.class;
    }

    @Override
    public void delete(Settori entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    sectorsDAO.delete(entity);
	}
	resetObjectCached();
    }

    @Override
    public List<Settori> findAll(Integer firstResult, Integer maxResult) {

	return sectorsDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Settori findById(SettoriId id) {

	return sectorsDAO.findById(id);
    }

    @Override
    public void insert(Settori entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity, true)) {
	    // forzo l'iserimento del codice maiuscolo
	    entity.getId().setCodicesettore(entity.getId().getCodicesettore().toUpperCase());
	    sectorsDAO.insert(entity);
	    // se si setta atrue controlla se ci sono dei settori disattivati a true e li setta a false
	    if (entity.getFlagContamqattivita().equals(true)) {
		this.updateSettoreAbilitatoAlconteggio(entity);
	    }
	    resetObjectCached();
	}
    }

    @Override
    public void update(Settori entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity, false)) {
	    sectorsDAO.update(entity);
	    // se si setta atrue controlla se ci sono dei settori disattivati a true e li setta a false
	    if (entity.getFlagContamqattivita().equals(true)) {
		this.updateSettoreAbilitatoAlconteggio(entity);
	    }
	    resetObjectCached();
	}
    }

    /**
     * Il metodo controlla tra quelli disattivati se esistono con flag "FlagContamqattivita"== true e li setta a false
     * 
     * @param entity
     */
    private void updateSettoreAbilitatoAlconteggio(Settori entity) {

	dataIntegration(entity);
	List<Settori> list = this.findAllSectoriAttiviOrDisattivi(true);
	for (Settori settori : list) {
	    if (settori.getFlagContamqattivita().equals(true)) {
		settori.setFlagContamqattivita(false);
		sectorsDAO.update(settori);
	    }
	}
    }

    @Override
    public List<Settori> findByFilter(Settori entity) {

	return sectorsDAO.findByFilter(entity);
    }

    @Override
    public List<Settori> findByFilterTable(FilterTable filterTable) {

	return sectorsDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Settori> findAllSectoriAttiviOrDisattivi(boolean flagDisattivi) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("flagDisabilitato", flagDisattivi, Boolean.class));
	ft.addRestriction(fr);
	List<Settori> risultato = this.findByFilterTable(ft);
	return risultato;
    }

    /**
     * Il metodo controlla se esiste già un record con il flag "flagContamqattivita" uguale a true se e solo se
     * l'oggetto settori passato ha il flag "flagContamqattivita" uguale a true
     * 
     * @param entity
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected boolean isInsertAllowed(Settori entity, boolean update) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// controllare che non estista per il software e l'idcomune in considerazione uno record con il flag
	// "flagContamqattivita"
	// uguale a true (puo essercerne uno solo per software e comune)
	// deve essere controllato che se si trova già uno configurato come "si" non sia quello stesso che stiamo passando come entity
	//(altrimenti sarebbe impossibile andare afare modifiche su questo record (problema in update)).
	if (entity.getFlagContamqattivita() != null && entity.getFlagContamqattivita() == true) {
	    List<Settori> list = this.findAllSectoriAttiviOrDisattivi(false);
	    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
		Settori settori = (Settori) iterator.next();
		if (settori.getFlagContamqattivita() != null && settori.getFlagContamqattivita() == true
			&& !settori.getId().getCodicesettore().equals(entity.getId().getCodicesettore())) {
		    _ivs.add(new InvalidValue("settori.service_error.flagContamqattivita", null, null, "", null));
		    break;
		}
	    }
	}
	if (update) {
	    // controlla che già non esista un recordo con quel codice settore
	    // forzo il codice a maiuscolo la ricerca deve essere caseInsenitive
	    entity.getId().setCodicesettore(entity.getId().getCodicesettore().toUpperCase());
	    Settori objectDB = null;
	    objectDB = sectorsDAO.findById(entity.getId());
	    if (objectDB != null && !objectDB.getId().getCodicesettore().equals("")) {
		_ivs.add(new InvalidValue("service_error.duplicate_codice", null, null, entity.getId().getCodicesettore(), null));
	    }
	}
	if (entity.getFlagContamqattivita().equals(true) && entity.getTipiunitamisura() == null) {
	    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto_se_conteggio_true", entity.getClass(), "tipiunitamisura", entity
		    .getTipiunitamisura(), entity));
	}
	if (!_ivs.isEmpty()) {
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
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction softwareAttivoAndTT = new FilterRestriction();
	    softwareAttivoAndTT.addFilterField(FilterUtils.in("software.codice", new String[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() },
		    String.class));
	    filterTable.addRestriction(softwareAttivoAndTT);
	    boolean exists = sectorsDAO.existsRecords(filterTable);
	    isRecordPresentiMap.put(key, Boolean.valueOf(exists));
	    return exists;
	}
    }

    @Override
    protected boolean isDeleteAllowed(Settori entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	List<Attivita> atts = attivitaService.findAttivitaBySettore(entity.getId().getCodicesettore(), "id.codiceistat");
	if (atts.size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ATTIVITA", null));
	}
	// Se trovo errori rilancio subito l'eccezione e blocco la cancellazione
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    private void dataIntegration(Settori entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il Documento passato è nullo");
	}
	fixMergeEntityProperties(entity);
	if (entity.getFlagContamqattivita() == null) {
	    entity.setFlagContamqattivita(false);
	}
	if (entity.getFlagDisabilitato() == null) {
	    entity.setFlagDisabilitato(false);
	}
	if (entity.getFlagInsmultiplo() == null) {
	    entity.setFlagInsmultiplo(false);
	}
	if (entity.getFoRichiesto() == null) {
	    entity.setFoRichiesto(false);
	}
    }

    protected void fixMergeEntityProperties(Settori entity) {

	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
	Tipiunitamisura tipiunitamisura = tipiunitamisuraService.bindDomainObject(entity.getTipiunitamisura(), PkId.class, "id.codice");
	entity.setTipiunitamisura(tipiunitamisura);
    }
}
