package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AreeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.DehorsAree;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AreeService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.DehorsAreeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreeServiceImpl extends BaseServiceImpl<Aree, PkId> implements AreeService {

    private static final Logger log = LoggerFactory.getLogger(AreeServiceImpl.class);
    private AreeDAO areeDAO;
    private ComuniassociatiService comuniassociatiService;
    private DehorsAreeService dehorsAreeService;

    @Autowired
    public void setAreeDAO(AreeDAO areeDAO) {

	this.areeDAO = areeDAO;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setDehorsAreeService(DehorsAreeService dehorsAreeService) {

	this.dehorsAreeService = dehorsAreeService;
    }

    @Override
    protected Class<Aree> getEntityClass() {

	return Aree.class;
    }

    @Override
    public List<Aree> findByDescrizione(String descrizione, String codiceComune) {

	List<Responsabilicomuni> comuniList = comuniassociatiService.checkComuniAbilitatiPerResponsabile(true);
	String[] codiciComune = responsabiliComuniToCodici(comuniList);
	return areeDAO.findByDescrizione(descrizione, codiceComune, codiciComune);
    }

    @Override
    public List<Aree> findByDescrizioneDehors(String textToSearch, String codiceComune) {

	List<Responsabilicomuni> comuniList = comuniassociatiService.checkComuniAbilitatiPerResponsabile(true);
	String[] codiciComune = responsabiliComuniToCodici(comuniList);
	return areeDAO.findByDescrizioneDehors(textToSearch, codiceComune, codiciComune);
    }

    @Override
    public void delete(Aree entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    // con la cascade rimuove anche i lotti
	    areeDAO.delete(entity);
	    resetObjectCached();
	}
    }

    @Override
    public List<Aree> findAll(Integer firstResult, Integer maxResult) {

	List<Responsabilicomuni> comuniList = comuniassociatiService.checkComuniAbilitatiPerResponsabile(true);
	List<Aree> areeList = null;
	if (comuniList.isEmpty()) {
	    areeList = areeDAO.findAll(firstResult, maxResult);
	} else {
	    areeList = this.findByComuniAbilitati(comuniList);
	}
	return areeList;
    }

    private List<Aree> findByComuniAbilitati(List<Responsabilicomuni> comuniList) {

	if (comuniList == null || comuniList.size() == 0) {
	    return null;
	}
	String[] codiciComune = responsabiliComuniToCodici(comuniList);
	return areeDAO.findAllByCodiciComuni(codiciComune);
    }

    @Override
    public Aree findById(PkId id) {

	return areeDAO.findById(id);
    }

    @Override
    public void insert(Aree entity) {

	if (validateEntity(entity)) {
	    areeDAO.insert(entity);
	    childInsert(entity);
	    resetObjectCached();
	}
    }

    @Override
    public void update(Aree entity) {

	if (validateEntity(entity)) {
	    areeDAO.update(entity);
	    childUpdate(entity);
	    resetObjectCached();
	}
    }

    public List<Aree> findByFilterTable(FilterTable filterTable) {

	return areeDAO.findByFilterTable(filterTable);
    }

    protected boolean isDeleteAllowed(Aree entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getCcCoeffcontributos().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "CC_COEFFCONTRIBUTO", null));
	}
	if (entity.getCcIcalcoloDcontributos().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "CC_ICALCOLO_DCONTRIBUTO", null));
	}
	if (entity.getIstanzearees().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZEAREE", null));
	}
	if (entity.getOIcalcolocontribtsForFkOicalcolocontprgAree().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "O_ICALCOLOCONTRIBT", null));
	}
	if (entity.getOIcalcolocontribtsForFkOicalcolocontztoAree().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "O_ICALCOLOCONTRIBT", null));
	}
	if (entity.getOTabellaabcsForFkOtabellaabcprgAree().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "O_TABELLAABC", null));
	}
	if (entity.getOTabellaabcsForFkOtabellaabcztoAree().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "O_TABELLAABC", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    private String[] responsabiliComuniToCodici(List<Responsabilicomuni> comuniList) {

	String[] codiciComune = new String[comuniList.size()];
	for (int i = 0; i < comuniList.size(); i++) {
	    codiciComune[i] = comuniList.get(i).getComune().getCodicecomune();
	}
	return codiciComune;
    }

    private Map<String, Boolean> isRecordPresentiMap = new HashMap<String, Boolean>();

    private String getMapKey(String idcomunealias, String software) {

	return idcomunealias + "-" + software;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	this.isRecordPresentiMap = new HashMap<String, Boolean>();
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
	    FilterTable areeFt = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction areeSoftwareAndTT = new FilterRestriction();
	    areeSoftwareAndTT.addFilterField(FilterUtils.in("software.codice", new String[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT },
		    String.class));
	    areeFt.addRestriction(areeSoftwareAndTT);
	    boolean exists = areeDAO.existsRecords(areeFt);
	    isRecordPresentiMap.put(key, Boolean.valueOf(exists));
	    return exists;
	}
    }

    @Override
    public List<Aree> findAreeByIstanza(Istanze istanza) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction ft = new FilterRestriction();
	ft.addFilterField(FilterUtils.equals("istanza", istanza, "istanzearees", Istanze.class));
	filterTable.addRestriction(ft);
	return this.findByFilterTable(filterTable);
    }

    private void childUpdate(Aree entity) {

	List<DehorsAree> list = dehorsAreeService.findByArea(entity.getId().getCodice());
	DehorsAree dehorsAree = null;
	if (entity.getMqdisponibili() != null) {
	    BigDecimal mqDisponibili = entity.getMqdisponibili();
	    if (list.isEmpty()) {
		log.debug("childUpdate#Inserisco un record in DEHORS_AREE con mq disponibili {} ", mqDisponibili);
		dehorsAree = new DehorsAree();
		dehorsAree.setAree(entity);
		dehorsAree.setMqdisponibili(mqDisponibili);
		dehorsAreeService.insert(dehorsAree);
	    } else {
		log.debug("childUpdate# Record già esistente, lo aggiorno con il nuovo valore.");
		dehorsAree = list.get(0);
		if (dehorsAree.getMqdisponibili().doubleValue() != 0) {
		    log.debug("childUpdate# Il nuovo valore è diverso da 0, aggiorno");
		    dehorsAree.setMqdisponibili(mqDisponibili);
		    dehorsAreeService.update(dehorsAree);
		} else {
		    log.debug("childUpdate# Il nuovo valore è uguale da 0, elimino il record");
		    dehorsAreeService.delete(dehorsAree);
		}
	    }
	} else {
	    if (!list.isEmpty()) {
		dehorsAree = list.get(0);
		dehorsAreeService.delete(dehorsAree);
	    }
	}
    }

    private void childInsert(Aree entity) {

	if (entity.getMqdisponibili() != null && entity.getMqdisponibili().doubleValue() != 0) {
	    BigDecimal mqDisponibili = entity.getMqdisponibili();
	    log.debug("childInsert#Inserisco un record in DEHORS_AREE con mq disponibili {} ", mqDisponibili);
	    DehorsAree dehorsAree = new DehorsAree();
	    dehorsAree.setAree(entity);
	    dehorsAree.setMqdisponibili(mqDisponibili);
	    dehorsAreeService.insert(dehorsAree);
	}
    }

    protected void childDelete(Aree entity) {

	if (!entity.getDehorsArees().isEmpty()) {
	    log.debug("childDelete#cancello i record in DEHORS_AREE con codice area{} ", entity.getId().getCodice());
	    List<DehorsAree> dehorsArees = dehorsAreeService.findByArea(entity.getId().getCodice());
	    for (DehorsAree dehorsAree : dehorsArees) {
		dehorsAreeService.delete(dehorsAree);
	    }
	}
    }
}
